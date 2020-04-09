package br.com.breadware.subscriber.message.analyser;

import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.model.MessageAnalysisContext;
import br.com.breadware.subscriber.message.analyser.link.MessageAnalyserAssociateExtractorLink;
import br.com.breadware.subscriber.message.analyser.link.MessageAnalyserLink;
import br.com.breadware.subscriber.message.analyser.link.MessageAnalyserValidatorLink;
import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class MessageAnalyser {

    private final MessageAnalyserValidatorLink messageAnalyserValidatorLink;

    private final MessageAnalyserAssociateExtractorLink messageAnalyserAssociateExtractorLink;

    private final MessageAnalyserLink first;

    @Inject
    public MessageAnalyser(MessageAnalyserValidatorLink messageAnalyserValidatorLink, MessageAnalyserAssociateExtractorLink messageAnalyserAssociateExtractorLink) {
        this.messageAnalyserValidatorLink = messageAnalyserValidatorLink;
        this.messageAnalyserAssociateExtractorLink = messageAnalyserAssociateExtractorLink;
        this.first = createChain();
    }

    private MessageAnalyserLink createChain() {
        messageAnalyserValidatorLink.setNext(messageAnalyserAssociateExtractorLink);
        return messageAnalyserValidatorLink;
    }

    public boolean analyse(Message message) throws MessageAnalysisException {

        MessageAnalysisContext messageAnalysisContext = new MessageAnalysisContext(message);

        return first.analyse(messageAnalysisContext);
    }
}
