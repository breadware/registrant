package br.com.breadware.google.cloud.pubsub;

import br.com.breadware.bo.GmailHistoryBo;
import br.com.breadware.bo.GmailMessageBo;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.model.GmailHistoryEvent;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.util.LoggerUtil;
import com.google.api.services.gmail.model.History;
import com.google.api.services.gmail.model.Message;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Component
public class GmailInboxHistoryEventMessageReceiver implements MessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailInboxHistoryEventMessageReceiver.class);

    private final LoggerUtil loggerUtil;

    private final GmailMessageBo gmailMessageBo;

    private final GmailHistoryBo gmailHistoryBo;

    @Inject
    public GmailInboxHistoryEventMessageReceiver(LoggerUtil loggerUtil, GmailMessageBo gmailMessageBo, GmailHistoryBo gmailHistoryBo) {
        this.loggerUtil = loggerUtil;
        this.gmailMessageBo = gmailMessageBo;
        this.gmailHistoryBo = gmailHistoryBo;
    }

    @Override
    public void receiveMessage(PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {
        boolean shouldAcknowledge = false;

        try {

            loggerUtil.info(LOGGER, LoggerMessage.SUBSCRIPTION_MESSAGE_RECEIVED, pubsubMessage.getMessageId());

            GmailHistoryEvent gmailHistoryEvent = gmailHistoryBo.map(pubsubMessage);

            List<History> histories = gmailHistoryBo.retrieveHistories(gmailHistoryEvent);

            Set<Message> messages = gmailHistoryBo.retrieveMessages(histories);

            removeAlreadyHandledMessages(messages);
            gmailMessageBo.removeSentMessages(messages);

            messages.forEach(gmailMessageBo::handle);

            gmailHistoryBo.setLastHandledEvent(gmailHistoryEvent);

            shouldAcknowledge = true;

        } catch (Exception exception) {
            loggerUtil.error(LOGGER, exception, ErrorMessage.ERROR_WHILE_HANDLING_SUBSCRIPTION_MESSAGE, pubsubMessage.getMessageId());
        }

        if (shouldAcknowledge) {
            ackReplyConsumer.ack();
        } else {
            ackReplyConsumer.nack();
        }
    }

    private void removeAlreadyHandledMessages(Set<Message> messages) throws DataAccessException {
        Set<Message> alreadyHandledMessages = gmailMessageBo.retrieveAlreadyHandledMessages(messages);
        alreadyHandledMessages.forEach(this::logMessageAlreadyHandled);
        messages.removeAll(alreadyHandledMessages);
    }

    private void logMessageAlreadyHandled(Message message) {
        loggerUtil.info(LOGGER, LoggerMessage.GMAIL_MESSAGE_HAS_ALREADY_BEEN_HANDLED, message.getId());
    }

}
