package br.com.breadware.subscriber.message.analyser;

import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.subscriber.message.analyser.link.MessageAnalyserCheckAssociateAlreadyExistsLink;
import br.com.breadware.subscriber.message.analyser.link.MessageAnalyserAssociateExtractorLink;
import br.com.breadware.subscriber.message.analyser.link.template.MessageAnalyserLink;
import br.com.breadware.subscriber.message.analyser.link.MessageAnalyserValidatorLink;
import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class MessageAnalyser {

    private final MessageAnalyserValidatorLink messageAnalyserValidatorLink;

    private final MessageAnalyserAssociateExtractorLink messageAnalyserAssociateExtractorLink;

    private final MessageAnalyserCheckAssociateAlreadyExistsLink messageAnalyserCheckAssociateAlreadyExistsLink;

    private final MessageAnalyserLink first;

    @Inject
    public MessageAnalyser(MessageAnalyserValidatorLink messageAnalyserValidatorLink, MessageAnalyserAssociateExtractorLink messageAnalyserAssociateExtractorLink, MessageAnalyserCheckAssociateAlreadyExistsLink messageAnalyserCheckAssociateAlreadyExistsLink) {
        this.messageAnalyserValidatorLink = messageAnalyserValidatorLink;
        this.messageAnalyserAssociateExtractorLink = messageAnalyserAssociateExtractorLink;
        this.messageAnalyserCheckAssociateAlreadyExistsLink = messageAnalyserCheckAssociateAlreadyExistsLink;
        this.first = createChain();
    }

    private MessageAnalyserLink createChain() {
        messageAnalyserValidatorLink.setNext(messageAnalyserAssociateExtractorLink);
        messageAnalyserAssociateExtractorLink.setNext(messageAnalyserCheckAssociateAlreadyExistsLink);
        return messageAnalyserValidatorLink;
    }

    public MessageAnalysisResult analyse(Message message) throws MessageAnalysisException {
        return first.analyse(new MessageAnalysisContext(message));
    }
}
