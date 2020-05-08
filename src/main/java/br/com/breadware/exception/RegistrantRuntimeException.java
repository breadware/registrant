package br.com.breadware.exception;

import br.com.breadware.configuration.RegistrantApplicationContextAware;
import br.com.breadware.model.message.ErrorMessage;

public class RegistrantRuntimeException extends RuntimeException {

    public RegistrantRuntimeException(String message) {
        super(message);
    }

    public RegistrantRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrantRuntimeException(ErrorMessage errorMessage, Object... parameters) {
        super(RegistrantRuntimeException.retrieveMessage(errorMessage, parameters));
    }

    public RegistrantRuntimeException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(RegistrantRuntimeException.retrieveMessage(errorMessage, parameters), cause);
    }

    private static String retrieveMessage(ErrorMessage errorMessage, Object... parameters) {
        return RegistrantApplicationContextAware.retrieveMessageRetriever()
                .map(messageRetriever -> messageRetriever.getMessage(errorMessage, parameters))
                .orElseGet(errorMessage::name);
    }
}
