package br.com.breadware.exception;

import br.com.breadware.model.message.ErrorMessage;

public class AuthorizationRequestRuntimeException extends RegistrantRuntimeException {

    public AuthorizationRequestRuntimeException(ErrorMessage errorMessage, Object... parameters) {
        super(errorMessage, parameters);
    }
}
