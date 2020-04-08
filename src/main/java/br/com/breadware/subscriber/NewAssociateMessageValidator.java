package br.com.breadware.subscriber;

import br.com.breadware.exception.MimeMessageHandlingException;
import br.com.breadware.model.Associate;
import br.com.breadware.model.mapper.MessageToMimeMessageMapper;
import br.com.breadware.util.LoggerUtil;
import br.com.breadware.util.MimeMessageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.gmail.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Component
public class NewAssociateMessageValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewAssociateMessageValidator.class);

    private final MessageToMimeMessageMapper messageToMimeMessageMapper;

    private final MimeMessageUtil mimeMessageUtil;

    private final ObjectMapper objectMapper;

    private final LoggerUtil loggerUtil;

    @Inject
    public NewAssociateMessageValidator(MessageToMimeMessageMapper messageToMimeMessageMapper, MimeMessageUtil mimeMessageUtil, ObjectMapper objectMapper, LoggerUtil loggerUtil) {
        this.messageToMimeMessageMapper = messageToMimeMessageMapper;
        this.mimeMessageUtil = mimeMessageUtil;
        this.objectMapper = objectMapper;
        this.loggerUtil = loggerUtil;
    }

    public boolean validate(Message message) throws MessagingException, MimeMessageHandlingException {

        MimeMessage mimeMessage = messageToMimeMessageMapper.map(message);
        String messageContent = mimeMessageUtil.retrieveContentAsText(mimeMessage);

        Optional<Associate> optionalAssociate = tryMapMessageContent(messageContent);

        // TODO Complete method.
        return false;
    }

    private Optional<Associate> tryMapMessageContent(String messageContent) {
        try {
            Associate associate = objectMapper.readValue(messageContent, Associate.class);
        } catch (JsonProcessingException e) {
            // TODO Handle exception correctly.
            e.printStackTrace();
        }

        // TODO Return optional with new associate information.
        return Optional.empty();
    }
}
