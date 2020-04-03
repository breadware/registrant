package br.com.breadware.exception;

import br.com.breadware.model.message.ErrorMessage;

public class DataAccessException extends RegistrantException {

    public DataAccessException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(cause, errorMessage, parameters);
    }
}
