package br.com.breadware.exception;

import br.com.breadware.model.message.ErrorMessage;

public class MappingException extends RegistrantRuntimeException {
    public MappingException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(cause, errorMessage, parameters);
    }

    public MappingException(ErrorMessage errorMessage, Object... parameters) {
        super(errorMessage, parameters);
    }
}
