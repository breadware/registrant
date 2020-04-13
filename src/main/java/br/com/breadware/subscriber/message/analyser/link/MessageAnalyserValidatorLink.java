package br.com.breadware.subscriber.message.analyser.link;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.exception.MimeMessageHandlingException;
import br.com.breadware.subscriber.message.analyser.MessageAnalysisContext;
import br.com.breadware.model.mapper.MessageToMimeMessageMapper;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.properties.EmailProperties;
import br.com.breadware.subscriber.message.analyser.MessageAnalysisStatus;
import br.com.breadware.subscriber.message.analyser.link.template.AbstractMessageAnalyserLink;
import br.com.breadware.util.LoggerUtil;
import br.com.breadware.util.MimeMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Component(BeanNames.MESSAGE_VALIDATOR)
public class MessageAnalyserValidatorLink extends AbstractMessageAnalyserLink {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAnalyserValidatorLink.class);

    public static final int MAX_MESSAGE_SIZE = 64;

    private final MessageToMimeMessageMapper messageToMimeMessageMapper;

    private final MimeMessageUtil mimeMessageUtil;

    private final LoggerUtil loggerUtil;

    private final EmailProperties emailProperties;

    @Inject
    public MessageAnalyserValidatorLink(MessageToMimeMessageMapper messageToMimeMessageMapper, MimeMessageUtil mimeMessageUtil, LoggerUtil loggerUtil, EmailProperties emailProperties) {
        this.messageToMimeMessageMapper = messageToMimeMessageMapper;
        this.mimeMessageUtil = mimeMessageUtil;
        this.loggerUtil = loggerUtil;
        this.emailProperties = emailProperties;
    }

    @Override
    public MessageAnalysisContext doAnalyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException {
        try {
            MimeMessage mimeMessage = messageToMimeMessageMapper.map(messageAnalysisContext.getMessage());
            messageAnalysisContext.setMimeMessage(Optional.of(mimeMessage));

            if (!checkSender(mimeMessage)) {
                messageAnalysisContext.setStatus(MessageAnalysisStatus.INVALID_MESSAGE);
                return messageAnalysisContext;
            }

            String messageContent = mimeMessageUtil.retrieveContentAsText(mimeMessage);
            messageAnalysisContext.setMessageContent(Optional.of(messageContent));

            if (!isValidJson(messageContent)) {
                messageAnalysisContext.setStatus(MessageAnalysisStatus.INVALID_MESSAGE);
                return messageAnalysisContext;
            }

            return messageAnalysisContext;

        } catch (MessagingException | MimeMessageHandlingException exception) {
            throw new MessageAnalysisException(exception, ErrorMessage.ERROR_WHILE_VALIDATING_MESSAGE);
        }
    }

    private boolean checkSender(MimeMessage mimeMessage) throws MessagingException {
        Address[] senders = mimeMessage.getFrom();

        int index = 0;
        boolean foundCorrectSender = false;

        while (index < senders.length && !foundCorrectSender) {
            Address sender = senders[index];
            if ( sender instanceof InternetAddress ) {
                InternetAddress internetAddress = (InternetAddress)sender;
                foundCorrectSender = emailProperties.getSender().equals(internetAddress.getAddress());
            }
            index++;
        }

        return foundCorrectSender;
    }

    private boolean isValidJson(String messageContent) {
        try {
            new JSONObject(messageContent);
            return true;
        } catch (JSONException exception) {
            String partMessage = StringUtils.abbreviate(messageContent, MAX_MESSAGE_SIZE);
            loggerUtil.info(LOGGER, LoggerMessage.MESSAGE_BODY_IS_INVALID_JSON, partMessage, exception.getMessage());
        }

        return false;
    }
}
