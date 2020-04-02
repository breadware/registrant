package br.com.breadware.exception;

import br.com.breadware.model.message.ErrorMessage;

public class MimeMessageHandlingException extends RegistrantException {
    public MimeMessageHandlingException(ErrorMessage errorMessage, Object... parameters) {
        super(errorMessage, parameters);
    }

    public MimeMessageHandlingException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(cause, errorMessage, parameters);
    }
}
