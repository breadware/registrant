package br.com.breadware.google.mail.message.analyser.link.template;

import br.com.breadware.exception.MessageAnalysisException;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisContext;
import br.com.breadware.google.mail.message.analyser.model.MessageAnalysisResult;

public interface MessageAnalyserLink {

    MessageAnalysisResult analyse(MessageAnalysisContext messageAnalysisContext) throws MessageAnalysisException;

    MessageAnalyserLink getNext();

    void setNext(MessageAnalyserLink messageAnalyserLink);
}
