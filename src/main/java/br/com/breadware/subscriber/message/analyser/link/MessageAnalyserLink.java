package br.com.breadware.subscriber.message.analyser.link;

import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.model.MessageAnalysisContext;

public interface MessageAnalyserLink {

    boolean analyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException;

    MessageAnalyserLink getNext();

    void setNext(MessageAnalyserLink messageAnalyserLink);
}
