package br.com.breadware.exception;

import br.com.breadware.configuration.RegistrantApplicationContextAware;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.util.MessageRetriever;

public class RegistrantRuntimeException extends RuntimeException {

    private static final MessageRetriever MESSAGE_RETRIEVER = RegistrantApplicationContextAware.retrieveBean(MessageRetriever.class);

    public RegistrantRuntimeException(String message) {
        super(message);
    }

    public RegistrantRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrantRuntimeException(ErrorMessage errorMessage, Object... parameters) {
        super(MESSAGE_RETRIEVER.getMessage(errorMessage, parameters));
    }

    public RegistrantRuntimeException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(MESSAGE_RETRIEVER.getMessage(errorMessage, parameters), cause);
    }
}
