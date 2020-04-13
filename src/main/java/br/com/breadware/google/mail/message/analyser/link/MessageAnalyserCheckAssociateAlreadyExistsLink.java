package br.com.breadware.google.mail.message.analyser.link;

import br.com.breadware.bo.AssociateBo;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisContext;
import br.com.breadware.google.mail.message.analyser.link.template.AbstractMessageAnalyserLink;
import br.com.breadware.model.Associate;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisStatus;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class MessageAnalyserCheckAssociateAlreadyExistsLink extends AbstractMessageAnalyserLink {

    private AssociateBo associateBo;

    @Inject
    public MessageAnalyserCheckAssociateAlreadyExistsLink(AssociateBo associateBo) {
        this.associateBo = associateBo;
    }

    @Override
    protected MessageAnalysisContext doAnalyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException {
        try {
            List<Associate> associates = associateBo.getAll();

            MessageAnalysisStatus messageAnalysisStatus;
            if (associates.contains(messageAnalysisContext.getAssociate())) {
                messageAnalysisStatus = MessageAnalysisStatus.DUPLICATED_ASSOCIATE;
            } else {
                messageAnalysisStatus = MessageAnalysisStatus.NEW_ASSOCIATE;
            }
            messageAnalysisContext.setStatus(messageAnalysisStatus);
            return messageAnalysisContext;
        } catch (DataAccessException exception) {
            throw new MessageAnalysisException(exception, ErrorMessage.ERROR_WHILE_CHECKING_ASSOCIATE_ALREADY_EXISTS);
        }
    }
}
