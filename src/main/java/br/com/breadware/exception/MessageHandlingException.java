package br.com.breadware.exception;

import br.com.breadware.model.message.ErrorMessage;

public class MessageHandlingException extends RegistrantRuntimeException {
    public MessageHandlingException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(cause, errorMessage, parameters);
    }
}
