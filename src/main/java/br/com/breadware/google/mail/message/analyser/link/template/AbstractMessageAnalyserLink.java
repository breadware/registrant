package br.com.breadware.google.mail.message.analyser.link.template;

import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisContext;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisResult;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisStatus;

import java.util.Collections;
import java.util.Set;

public abstract class AbstractMessageAnalyserLink implements MessageAnalyserLink {

    private static final Set<MessageAnalysisStatus> INTERRUPT_CHAIN_STATUSES;

    static {
        INTERRUPT_CHAIN_STATUSES = Collections.singleton(MessageAnalysisStatus.INVALID_MESSAGE);
    }

    private MessageAnalyserLink next;

    @Override
    public MessageAnalysisResult analyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException {

        messageAnalysisContext = doAnalyse(messageAnalysisContext);

        if (INTERRUPT_CHAIN_STATUSES.contains(messageAnalysisContext.getStatus())) {
            return createMessageAnalysisResult(messageAnalysisContext);
        }

        if (next != null) {
            return getNext().analyse(messageAnalysisContext);
        }

        return createMessageAnalysisResult(messageAnalysisContext);
    }

    private MessageAnalysisResult createMessageAnalysisResult(MessageAnalysisContext messageAnalysisContext) {
        return MessageAnalysisResult.Builder.aMessageAnalysisResult()
                .associate(messageAnalysisContext.getAssociate())
                .status(messageAnalysisContext.getStatus())
                .build();
    }

    @Override
    public MessageAnalyserLink getNext() {
        return next;
    }

    @Override
    public void setNext(MessageAnalyserLink messageAnalyserLink) {
        this.next = messageAnalyserLink;
    }

    protected abstract MessageAnalysisContext doAnalyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException;
}
