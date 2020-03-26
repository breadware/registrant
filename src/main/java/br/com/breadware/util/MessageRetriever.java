package br.com.breadware.util;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.model.message.Message;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Locale;

@Component(BeanNames.MESSAGE_RETRIEVER)
public class MessageRetriever {

    private final MessageSource messageSource;

    private final Locale locale;

    @Inject
    public MessageRetriever(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    public String getMessage(Message message, Object... parameters) {
        return messageSource.getMessage(message.getCode(), parameters, locale);
    }
}
