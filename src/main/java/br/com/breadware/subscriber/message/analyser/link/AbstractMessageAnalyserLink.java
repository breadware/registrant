package br.com.breadware.subscriber.message.analyser.link;

import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.model.MessageAnalysisContext;

public abstract class AbstractMessageAnalyserLink implements MessageAnalyserLink {

    private MessageAnalyserLink next;

    @Override
    public boolean analyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException {

        if (!doAnalyse(messageAnalysisContext)) {
            return false;
        }

        if (next != null) {
            return getNext().analyse(messageAnalysisContext);
        }

        return true;
    }

    @Override
    public MessageAnalyserLink getNext() {
        return next;
    }

    @Override
    public void setNext(MessageAnalyserLink messageAnalyserLink) {
        this.next = messageAnalyserLink;
    }

    protected abstract boolean doAnalyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException;
}
