package br.com.breadware.exception;

import br.com.breadware.configuration.RegistrantApplicationContextAware;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.util.MessageRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrantRuntimeException extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrantRuntimeException.class);

    private static final MessageRetriever MESSAGE_RETRIEVER = RegistrantApplicationContextAware.retrieveBean(MessageRetriever.class);

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
        try {
            return MESSAGE_RETRIEVER.getMessage(errorMessage, parameters);
        } catch (ApplicationContextRuntimeException exception) {
            LOGGER.warn(exception.getMessage());
            return errorMessage.name();
        }
    }
}
