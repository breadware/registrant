package br.com.breadware.exception;

import br.com.breadware.model.message.ErrorMessage;

public class MapperException extends RegistrantRuntimeException{
    public MapperException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(cause, errorMessage, parameters);
    }
}
