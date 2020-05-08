package br.com.breadware.google.mail.message;

import br.com.breadware.configuration.GcpConfiguration;
import br.com.breadware.exception.GmailMessageRetrievalException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.util.LoggerUtil;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GmailMessageRetriever {

    public static final String MESSAGE_FORMAT = "raw";
    private static final Logger LOGGER = LoggerFactory.getLogger(GmailMessageRetriever.class);
    private final Gmail gmail;

    private final LoggerUtil loggerUtil;

    @Inject
    public GmailMessageRetriever(Gmail gmail, LoggerUtil loggerUtil) {
        this.gmail = gmail;
        this.loggerUtil = loggerUtil;
    }

    public List<Message> retrieveAll(List<String> messageIds) throws GmailMessageRetrievalException {

        List<Message> messages = new ArrayList<>(messageIds.size());

        for (String messageId : messageIds) {
            Message message = retrieve(messageId);
            messages.add(message);
        }

        return messages;
    }

    public Message retrieve(String messageId) throws GmailMessageRetrievalException {
        loggerUtil.info(LOGGER, LoggerMessage.RETRIEVING_GMAIL_MESSAGE, messageId);

        return requestMessageForGmail(messageId);
    }

    private Message requestMessageForGmail(String messageId) throws GmailMessageRetrievalException {

        try {
            return gmail.users()
                    .messages()
                    .get(GcpConfiguration.USER_ID, messageId)
                    .setFormat(MESSAGE_FORMAT)
                    .execute();
        } catch (IOException exception) {
            ErrorMessage errorMessage = ErrorMessage.ERROR_WHILE_REQUESTING_GMAIL_MESSAGE;
            throw new GmailMessageRetrievalException(exception, errorMessage);
        }
    }

}
