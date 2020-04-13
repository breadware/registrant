package br.com.breadware.subscriber.message.analyser.link.template;

import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.subscriber.message.analyser.MessageAnalysisContext;
import br.com.breadware.subscriber.message.analyser.MessageAnalysisResult;

public interface MessageAnalyserLink {

    MessageAnalysisResult analyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException;

    MessageAnalyserLink getNext();

    void setNext(MessageAnalyserLink messageAnalyserLink);
}
