package br.com.breadware.google.mail.message.analyser.link;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.google.mail.message.analyser.link.template.AbstractMessageAnalyserLink;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisContext;
import br.com.breadware.model.Associate;
import br.com.breadware.model.message.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Objects;

@Component(BeanNames.ASSOCIATE_EXTRACTOR)
public class MessageAnalyserAssociateExtractorLink extends AbstractMessageAnalyserLink {

    private final ObjectMapper objectMapper;

    @Inject
    protected MessageAnalyserAssociateExtractorLink(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected MessageAnalysisContext doAnalyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException {
        try {
            if (Objects.isNull(messageAnalysisContext.getMessageContent())) {
                throw new MessageAnalysisException(ErrorMessage.COULD_NOT_FIND_MESSAGE_CONTENT);
            }

            Associate associate = objectMapper.readValue(messageAnalysisContext.getMessageContent(), Associate.class);

            messageAnalysisContext.setAssociate(associate);

            return messageAnalysisContext;
        } catch (JsonProcessingException exception) {
            throw new MessageAnalysisException(exception, ErrorMessage.ERROR_EXTRACTING_ASSOCIATE_INFORMATION);
        }
    }
}
