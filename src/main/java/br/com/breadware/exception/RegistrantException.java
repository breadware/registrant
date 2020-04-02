package br.com.breadware.exception;

import br.com.breadware.configuration.RegistrantApplicationContextAware;
import br.com.breadware.model.message.ErrorMessage;

public class RegistrantException extends Exception {

    public RegistrantException(String message) {
        super(message);
    }

    public RegistrantException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrantException(ErrorMessage errorMessage, Object... parameters) {
        super(RegistrantException.retrieveMessage(errorMessage, parameters));
    }

    public RegistrantException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(RegistrantException.retrieveMessage(errorMessage, parameters), cause);
    }

    private static String retrieveMessage(ErrorMessage errorMessage, Object... parameters) {
        return RegistrantApplicationContextAware.retrieveMessageRetriever()
                .map(messageRetriever -> messageRetriever.getMessage(errorMessage, parameters))
                .orElseGet(errorMessage::name);
    }
}
