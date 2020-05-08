package br.com.breadware.util;

import br.com.breadware.model.message.Message;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class LoggerUtil {

    private final MessageRetriever messageRetriever;

    @Inject
    public LoggerUtil(MessageRetriever messageRetriever) {
        this.messageRetriever = messageRetriever;
    }

    public void info(Logger logger, Message message, Object... parameters) {
        if (logger.isInfoEnabled()) {
            String messageContent = messageRetriever.getMessage(message, parameters);
            logger.info(messageContent);
        }
    }

    public void error(Logger logger, Throwable throwable, Message message, Object... parameters) {
        if (logger.isErrorEnabled()) {
            String messageContent = messageRetriever.getMessage(message, parameters);
            logger.error(messageContent, throwable);
        }
    }

    public void warn(Logger logger, Message message, Object... parameters) {
        if (logger.isErrorEnabled()) {
            String messageContent = messageRetriever.getMessage(message, parameters);
            logger.warn(messageContent);
        }
    }
}
