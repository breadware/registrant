package br.com.breadware.google.mail.message.analyser.link;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisContext;
import br.com.breadware.google.mail.message.analyser.link.template.AbstractMessageAnalyserLink;
import br.com.breadware.model.Associate;
import br.com.breadware.model.message.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;

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
            Optional<String> optionalMessageContent = messageAnalysisContext.getMessageContent();
            if (optionalMessageContent.isEmpty()) {
                // TODO Create correct ErrorMessage.
                throw new MessageAnalysisException(ErrorMessage.ERROR_CREATING_SUBSCRIBER);
            }

            Associate associate = objectMapper.readValue(optionalMessageContent.get(), Associate.class);

            messageAnalysisContext.setAssociate(Optional.of(associate));

            return messageAnalysisContext;
        } catch (JsonProcessingException exception) {
            // TODO Handle exception correctly.
            // TODO Create correct ErrorMessage.
            throw new MessageAnalysisException(exception, ErrorMessage.ERROR_CREATING_SUBSCRIBER);
        }
    }
}
