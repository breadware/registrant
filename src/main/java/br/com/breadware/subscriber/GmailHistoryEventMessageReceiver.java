package br.com.breadware.subscriber;

import br.com.breadware.bo.GmailIdsBo;
import br.com.breadware.model.GmailHistoryEvent;
import br.com.breadware.model.mapper.MessageToMimeMessageMapper;
import br.com.breadware.model.mapper.PubSubMessageToGmailHistoryEventMapper;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.util.LoggerUtil;
import br.com.breadware.util.MimeMessageUtil;
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
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GmailHistoryEventMessageReceiver implements MessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailHistoryEventMessageReceiver.class);

    private final PubSubMessageToGmailHistoryEventMapper pubSubMessageToGmailHistoryEventMapper;

    private final LoggerUtil loggerUtil;

    private final GmailHistoryRetriever gmailHistoryRetriever;

    private final GmailMessageRetriever gmailMessageRetriever;

    private final GmailIdsBo gmailIdsBo;

    private final MessageToMimeMessageMapper messageToMimeMessageMapper;

    private final MimeMessageUtil mimeMessageUtil;

    @Inject
    public GmailHistoryEventMessageReceiver(PubSubMessageToGmailHistoryEventMapper pubSubMessageToGmailHistoryEventMapper, LoggerUtil loggerUtil, GmailHistoryRetriever gmailHistoryRetriever, GmailMessageRetriever gmailMessageRetriever, GmailIdsBo gmailIdsBo, MessageToMimeMessageMapper messageToMimeMessageMapper, MimeMessageUtil mimeMessageUtil) {
        this.pubSubMessageToGmailHistoryEventMapper = pubSubMessageToGmailHistoryEventMapper;
        this.loggerUtil = loggerUtil;
        this.gmailHistoryRetriever = gmailHistoryRetriever;
        this.gmailMessageRetriever = gmailMessageRetriever;
        this.gmailIdsBo = gmailIdsBo;
        this.messageToMimeMessageMapper = messageToMimeMessageMapper;
        this.mimeMessageUtil = mimeMessageUtil;
    }

    @Override
    public void receiveMessage(PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {
        boolean shouldAcknowledge = false;

        loggerUtil.info(LOGGER, LoggerMessage.SUBSCRIPTION_MESSAGE_RECEIVED, pubsubMessage.getMessageId());

        GmailHistoryEvent gmailHistoryEvent = pubSubMessageToGmailHistoryEventMapper.map(pubsubMessage);

        if (gmailHistoryEvent.getId()
                .compareTo(gmailIdsBo.getLastHistoryId()) < 0) {
            loggerUtil.warn(LOGGER, LoggerMessage.EVENT_ID_RECEIVED_IS_PREVIOUS_TO_THE_LAST_ID, gmailHistoryEvent.getId(), gmailIdsBo.getLastHistoryId());
            gmailIdsBo.setLastHistoryId(gmailHistoryEvent.getId());
        }

        try {

            ListHistoryResponse listHistoryResponse = gmailHistoryRetriever.retrieve();

            List<String> messageIds = retrieveUnhandledMessageIds(listHistoryResponse);

            List<Message> messages = gmailMessageRetriever.retrieveAll(messageIds);

            for (Message message : messages) {

                MimeMessage mimeMessage = messageToMimeMessageMapper.map(message);

                String messageContent = mimeMessageUtil.retrieveContentAsText(mimeMessage);

                System.out.println(messageContent);

                gmailIdsBo.putMessageId(message.getId());
            }

            gmailIdsBo.setLastHistoryId(gmailHistoryEvent.getId());

            shouldAcknowledge = false;

        } catch (Exception exception) {
            loggerUtil.error(LOGGER, exception, ErrorMessage.ERROR_WHILE_HANDLING_MESSAGE, pubsubMessage.getMessageId());
        }

        if (shouldAcknowledge) {
            ackReplyConsumer.ack();
        } else {
            ackReplyConsumer.nack();
        }
    }


    private List<History> retrieveHistories(ListHistoryResponse listHistoryResponse) {
        List<History> histories = listHistoryResponse.getHistory();

        if (CollectionUtils.isEmpty(histories)) {
            loggerUtil.warn(LOGGER, LoggerMessage.GMAIL_HISTORY_RETRIEVAL_RETURNED_AN_EMPTY_LIST);
            return Collections.emptyList();
        }

        return histories;
    }

    private List<String> retrieveUnhandledMessageIds(ListHistoryResponse listHistoryResponse) {

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

    private void removeAlreadyHandledMessageIds(List<String> messageIds) {
        List<String> alreadyHandledMessageIds = messageIds.stream()
                .filter(gmailIdsBo::hasMessageId)
                .collect(Collectors.toList());

        alreadyHandledMessageIds.forEach(this::logMessageAlreadyHandled);

        messageIds.removeAll(alreadyHandledMessageIds);
    }

    private void logMessageAlreadyHandled(String messageId) {
        LOGGER.warn("Message {} has already been handled.", messageId);
    }

}
