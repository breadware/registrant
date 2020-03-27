package br.com.breadware.configuration;

import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.properties.MessagesProperties;
import br.com.breadware.util.PathUtil;
import com.google.cloud.pubsub.v1.MessageReceiver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class MessageSourceConfiguration {

    @Bean(BeanNames.MESSAGE_SOURCE)
    public MessageSource createMessageSource(MessagesProperties messagesProperties, PathUtil pathUtil) {
        String[] messageFilePaths = retrieveMessageFilePaths(messagesProperties, pathUtil);
        ReloadableResourceBundleMessageSource validationMessageSource = new ReloadableResourceBundleMessageSource();
        validationMessageSource.setBasenames(messageFilePaths);
        validationMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return validationMessageSource;
    }

    private String[] retrieveMessageFilePaths(MessagesProperties messagesProperties, PathUtil pathUtil) {
        List<String> messageFilePaths = new ArrayList<>(Arrays.asList(ErrorMessage.FILE_PATH, LoggerMessage.FILE_PATH));

        String messageFileBaseDirectoryPath = pathUtil.appendSeparatorIfNecessary(messagesProperties.getFileBaseDirectoryPath());

        messageFilePaths = messageFilePaths.stream()
                .map(messageFilePath -> messageFileBaseDirectoryPath + messageFilePath)
                .map(pathUtil::removeMultipleSeparators)
                .collect(Collectors.toList());
        return messageFilePaths.toArray(String[]::new);
    }
}
