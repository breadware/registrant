package br.com.breadware.subscriber;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.properties.GoogleCloudPlatformProperties;
import br.com.breadware.util.LoggerUtil;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@DependsOn(BeanNames.GMAIL_WATCH_REQUESTER)
public class SubscriberCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberCreator.class);

    private static final String ERROR_MESSAGE = "Something has happened with the subscriber.";

    private final GoogleCloudPlatformProperties googleCloudPlatformProperties;

    private final NewMailMessageReceiver newMailMessageReceiver;

    private final LoggerUtil loggerUtil;

    @Inject
    public SubscriberCreator(GoogleCloudPlatformProperties googleCloudPlatformProperties, NewMailMessageReceiver newMailMessageReceiver, LoggerUtil loggerUtil) {
        this.googleCloudPlatformProperties = googleCloudPlatformProperties;
        this.newMailMessageReceiver = newMailMessageReceiver;
        this.loggerUtil = loggerUtil;
    }

    public void createAndStart() {

        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(googleCloudPlatformProperties.getProjectId(), googleCloudPlatformProperties.getSubscriptionId());

        try {
            Subscriber subscriber = Subscriber.newBuilder(subscriptionName, newMailMessageReceiver).build();
            subscriber.startAsync();
            loggerUtil.info(LOGGER, LoggerMessage.SUBSCRIBER_CREATED);
        } catch (IllegalStateException exception) {
            // TODO Adjust exception treatment.
            // TODO Application context is not ready yet. Perhaps try to throw an error with a predefined message?
            LOGGER.error(ERROR_MESSAGE, exception);
            throw new RegistrantRuntimeException(ERROR_MESSAGE, exception);
        }
    }
}