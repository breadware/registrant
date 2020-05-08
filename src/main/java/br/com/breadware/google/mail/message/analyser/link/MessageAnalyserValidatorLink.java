package br.com.breadware.google.mail.message.analyser.link;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.exception.MimeMessageHandlingException;
import br.com.breadware.google.mail.message.analyser.link.template.AbstractMessageAnalyserLink;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisContext;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisStatus;
import br.com.breadware.model.mapper.MessageToMimeMessageTwoWayMapper;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.properties.EmailProperties;
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

@Component(BeanNames.MESSAGE_VALIDATOR)
public class MessageAnalyserValidatorLink extends AbstractMessageAnalyserLink {

    public static final int MAX_MESSAGE_SIZE = 64;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAnalyserValidatorLink.class);
    private final MessageToMimeMessageTwoWayMapper messageToMimeMessageTwoWayMapper;

    private final MimeMessageUtil mimeMessageUtil;

    private final LoggerUtil loggerUtil;

    private final EmailProperties emailProperties;

    @Inject
    public MessageAnalyserValidatorLink(MessageToMimeMessageTwoWayMapper messageToMimeMessageTwoWayMapper, MimeMessageUtil mimeMessageUtil, LoggerUtil loggerUtil, EmailProperties emailProperties) {
        this.messageToMimeMessageTwoWayMapper = messageToMimeMessageTwoWayMapper;
        this.mimeMessageUtil = mimeMessageUtil;
        this.loggerUtil = loggerUtil;
        this.emailProperties = emailProperties;
    }

    @Override
    public MessageAnalysisContext doAnalyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException {
        try {
            MimeMessage mimeMessage = messageToMimeMessageTwoWayMapper.mapTo(messageAnalysisContext.getMessage());
            messageAnalysisContext.setMimeMessage(mimeMessage);

            if (!checkSender(mimeMessage)) {
                messageAnalysisContext.setStatus(MessageAnalysisStatus.INVALID_MESSAGE);
                return messageAnalysisContext;
            }

            String messageContent = mimeMessageUtil.retrieveContentAsText(mimeMessage);
            messageAnalysisContext.setMessageContent(messageContent);

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
            if (sender instanceof InternetAddress) {
                InternetAddress internetAddress = (InternetAddress) sender;
                foundCorrectSender = emailProperties.getSender()
                        .equals(internetAddress.getAddress());
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
