package br.com.breadware.subscriber;

import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.properties.GoogleCloudPlatformProperties;
import br.com.breadware.util.EnvironmentVariableUtil;
import br.com.breadware.util.LoggerUtil;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class SubscriberCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberCreator.class);

    private static final String CREDENTIALS_ENVIRONMENT_VARIABLE_NAME = "GOOGLE_APPLICATION_CREDENTIALS";

    private final GoogleCloudPlatformProperties googleCloudPlatformProperties;

    private final NewMailMessageReceiver newMailMessageReceiver;

    private final LoggerUtil loggerUtil;

    private final EnvironmentVariableUtil environmentVariableUtil;

    @Inject
    public SubscriberCreator(GoogleCloudPlatformProperties googleCloudPlatformProperties, NewMailMessageReceiver newMailMessageReceiver, LoggerUtil loggerUtil, EnvironmentVariableUtil environmentVariableUtil) {
        this.googleCloudPlatformProperties = googleCloudPlatformProperties;
        this.newMailMessageReceiver = newMailMessageReceiver;
        this.loggerUtil = loggerUtil;
        this.environmentVariableUtil = environmentVariableUtil;
    }

    public void createAndStart() {

        environmentVariableUtil.throwExceptionIfDoesNotExist(CREDENTIALS_ENVIRONMENT_VARIABLE_NAME);

        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(googleCloudPlatformProperties.getProjectId(), googleCloudPlatformProperties.getSubscriptionId());

        try {
            Subscriber subscriber = Subscriber.newBuilder(subscriptionName, newMailMessageReceiver).build();
            subscriber.startAsync();
            loggerUtil.info(LOGGER, LoggerMessage.SUBSCRIBER_CREATED);
        } catch (IllegalStateException exception) {
            loggerUtil.error(LOGGER, exception, ErrorMessage.ERROR_CREATING_SUBSCRIBER);
            throw new RegistrantRuntimeException(ErrorMessage.ERROR_CREATING_SUBSCRIBER, exception);
        }
    }
}