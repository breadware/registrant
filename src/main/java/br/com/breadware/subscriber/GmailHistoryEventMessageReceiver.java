package br.com.breadware.subscriber;

import br.com.breadware.bo.HandledGmailMessageBo;
import br.com.breadware.bo.LastHistoryEventBo;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.model.GmailHistoryEvent;
import br.com.breadware.model.HandledGmailMessage;
import br.com.breadware.model.LastHistoryEvent;
import br.com.breadware.model.mapper.PubSubMessageToGmailHistoryEventMapper;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.subscriber.message.analyser.MessageAnalyser;
import br.com.breadware.util.LoggerUtil;
import com.google.api.services.gmail.model.History;
import com.google.api.services.gmail.model.HistoryMessageAdded;
import com.google.api.services.gmail.model.ListHistoryResponse;
import com.google.api.services.gmail.model.Message;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GmailHistoryEventMessageReceiver implements MessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailHistoryEventMessageReceiver.class);

    private final PubSubMessageToGmailHistoryEventMapper pubSubMessageToGmailHistoryEventMapper;

    private final LoggerUtil loggerUtil;

    private final GmailHistoryRetriever gmailHistoryRetriever;

    private final GmailMessageRetriever gmailMessageRetriever;

    private final HandledGmailMessageBo handledGmailMessageBo;

    private final LastHistoryEventBo lastHistoryEventBo;

    private final MessageAnalyser messageAnalyser;

    @Inject
    public GmailHistoryEventMessageReceiver(PubSubMessageToGmailHistoryEventMapper pubSubMessageToGmailHistoryEventMapper, LoggerUtil loggerUtil, GmailHistoryRetriever gmailHistoryRetriever, GmailMessageRetriever gmailMessageRetriever, HandledGmailMessageBo handledGmailMessageBo, LastHistoryEventBo lastHistoryEventBo, MessageAnalyser messageAnalyser) {
        this.pubSubMessageToGmailHistoryEventMapper = pubSubMessageToGmailHistoryEventMapper;
        this.loggerUtil = loggerUtil;
        this.gmailHistoryRetriever = gmailHistoryRetriever;
        this.gmailMessageRetriever = gmailMessageRetriever;
        this.handledGmailMessageBo = handledGmailMessageBo;
        this.lastHistoryEventBo = lastHistoryEventBo;
        this.messageAnalyser = messageAnalyser;
    }

    @Override
    public void receiveMessage(PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {
        boolean shouldAcknowledge = false;

        loggerUtil.info(LOGGER, LoggerMessage.SUBSCRIPTION_MESSAGE_RECEIVED, pubsubMessage.getMessageId());

        GmailHistoryEvent gmailHistoryEvent = pubSubMessageToGmailHistoryEventMapper.map(pubsubMessage);


        try {

            LastHistoryEvent lastHistoryEvent = lastHistoryEventBo.get();

            if (gmailHistoryEvent.getId()
                    .compareTo(lastHistoryEvent.getId()) < 0) {
                loggerUtil.warn(LOGGER, LoggerMessage.EVENT_ID_RECEIVED_IS_PREVIOUS_TO_THE_LAST_ID, gmailHistoryEvent.getId(), lastHistoryEvent.getId());
                updateLastHistoryEvent(gmailHistoryEvent, lastHistoryEvent);
            }

            ListHistoryResponse listHistoryResponse = gmailHistoryRetriever.retrieve();

            List<String> messageIds = retrieveUnhandledMessageIds(listHistoryResponse);

            List<Message> messages = gmailMessageRetriever.retrieveAll(messageIds);

            for (Message message : messages) {

                messageAnalyser.analyse(message);

                signalMessageAsHandled(message);
            }

            updateLastHistoryEvent(gmailHistoryEvent, lastHistoryEvent);

            shouldAcknowledge = true;

        } catch (Exception exception) {
            loggerUtil.error(LOGGER, exception, ErrorMessage.ERROR_WHILE_HANDLING_MESSAGE, pubsubMessage.getMessageId());
        }

        if (shouldAcknowledge) {
            ackReplyConsumer.ack();
        } else {
            ackReplyConsumer.nack();
        }
    }

    private void signalMessageAsHandled(Message message) throws DataAccessException {
        HandledGmailMessage handledGmailMessage = HandledGmailMessage.Builder.aHandledGmailMessage()
                .id(message.getId())
                .time(LocalDateTime.now()
                        .toEpochSecond(ZoneOffset.UTC))
                .build();

        handledGmailMessageBo.set(handledGmailMessage);
    }

    private void updateLastHistoryEvent(GmailHistoryEvent gmailHistoryEvent, LastHistoryEvent lastHistoryEvent) throws DataAccessException {
        lastHistoryEvent.setId(gmailHistoryEvent.getId());
        lastHistoryEventBo.set(lastHistoryEvent);
    }


    private List<History> retrieveHistories(ListHistoryResponse listHistoryResponse) {
        List<History> histories = listHistoryResponse.getHistory();

        if (CollectionUtils.isEmpty(histories)) {
            loggerUtil.warn(LOGGER, LoggerMessage.GMAIL_HISTORY_RETRIEVAL_RETURNED_AN_EMPTY_LIST);
            return Collections.emptyList();
        }

        return histories;
    }

    private List<String> retrieveUnhandledMessageIds(ListHistoryResponse listHistoryResponse) throws DataAccessException {

        List<History> histories = retrieveHistories(listHistoryResponse);

        List<String> messageIds = histories.stream()
                .map(History::getMessagesAdded)
                .flatMap(List::stream)
                .map(HistoryMessageAdded::getMessage)
                .map(Message::getId)
                .collect(Collectors.toList());

        removeAlreadyHandledMessageIds(messageIds);

        return messageIds;
    }

    private void removeAlreadyHandledMessageIds(List<String> messageIds) throws DataAccessException {

        Set<String> handledMessageIds = retrieveHandledMessageIds();

        List<String> alreadyHandledMessageIds = messageIds.stream()
                .filter(handledMessageIds::contains)
                .collect(Collectors.toList());

        alreadyHandledMessageIds.forEach(this::logMessageAlreadyHandled);

        messageIds.removeAll(alreadyHandledMessageIds);
    }

    private Set<String> retrieveHandledMessageIds() throws DataAccessException {
        return handledGmailMessageBo.getAll()
                .stream()
                .map(HandledGmailMessage::getId)
                .collect(Collectors.toSet());
    }

    private void logMessageAlreadyHandled(String messageId) {
        LOGGER.warn("Message {} has already been handled.", messageId);
    }

}
