package br.com.breadware.google.mail.message;

import br.com.breadware.bo.AssociateBo;
import br.com.breadware.exception.RegistrantException;
import br.com.breadware.google.mail.message.analyser.MessageAnalyser;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisResult;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisStatus;
import br.com.breadware.google.mail.message.composer.GmailMessageComposer;
import br.com.breadware.model.Associate;
import br.com.breadware.model.Email;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.util.LoggerUtil;
import com.google.api.services.gmail.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class GmailMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailMessageHandler.class);

    private final LoggerUtil loggerUtil;

    private final MessageAnalyser messageAnalyser;

    private final AssociateBo associateBo;

    private final GmailMessageComposer gmailMessageComposer;

    private final GmailMessageSender gmailMessageSender;

    @Inject
    public GmailMessageHandler(LoggerUtil loggerUtil, MessageAnalyser messageAnalyser, AssociateBo associateBo, GmailMessageComposer gmailMessageComposer, GmailMessageSender gmailMessageSender) {
        this.loggerUtil = loggerUtil;
        this.messageAnalyser = messageAnalyser;
        this.associateBo = associateBo;
        this.gmailMessageComposer = gmailMessageComposer;
        this.gmailMessageSender = gmailMessageSender;
    }

    public void handle(Message message) throws RegistrantException {

        MessageAnalysisResult messageAnalysisResult = messageAnalyser.analyse(message);

        switch (messageAnalysisResult.getStatus()) {
            case INVALID_MESSAGE:
                loggerUtil.info(LOGGER, LoggerMessage.MESSAGE_IS_NOT_ASSOCIATE_INFORMATION, message.getId());
                break;
            case DUPLICATED_ASSOCIATE:
            case NEW_ASSOCIATE:
                Associate associate = messageAnalysisResult.getAssociate();
                Email email = retrieveEmail(messageAnalysisResult.getStatus());
                associateBo.put(associate);
                Message answerEmail = gmailMessageComposer.compose(email, associate);
                gmailMessageSender.send(answerEmail);
                break;
            case UNDEFINED:
                throw new RegistrantException(ErrorMessage.INVALID_MESSAGE_ANALYSIS_STATUS_RESULT, messageAnalysisResult.getStatus());
        }
    }

    private Email retrieveEmail(MessageAnalysisStatus messageAnalysisStatus) {
        if (MessageAnalysisStatus.DUPLICATED_ASSOCIATE.equals(messageAnalysisStatus)) {
            return Email.EXISTING_ASSOCIATE;
        } else {
            return Email.NEW_ASSOCIATE;
        }
    }
}
