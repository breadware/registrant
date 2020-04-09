package br.com.breadware.exception;

import br.com.breadware.model.message.ErrorMessage;

public class MessageAnalysisException extends RegistrantException {

    public MessageAnalysisException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(cause, errorMessage, parameters);
    }

    public MessageAnalysisException(ErrorMessage errorMessage, Object... parameters) {
        super(errorMessage, parameters);
    }
}
