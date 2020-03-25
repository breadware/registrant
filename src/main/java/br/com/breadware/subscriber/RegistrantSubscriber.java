package br.com.breadware.subscriber;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.properties.GoogleCloudPlatformProperties;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
@DependsOn(BeanNames.GMAIL_WATCH_REQUESTER)
public class RegistrantSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrantSubscriber.class);

    private static final String ERROR_MESSAGE = "Something has happened with the subscriber.";

    private final GoogleCloudPlatformProperties googleCloudPlatformProperties;

    private final NewMailMessageReceiver newMailMessageReceiver;

    @Inject
    public RegistrantSubscriber(GoogleCloudPlatformProperties googleCloudPlatformProperties, NewMailMessageReceiver newMailMessageReceiver) {
        this.googleCloudPlatformProperties = googleCloudPlatformProperties;
        this.newMailMessageReceiver = newMailMessageReceiver;
    }

    @PostConstruct
    public void postConstruct() {
        createSubscriber();
    }

    public void createSubscriber() {

        // TODO Check "GOOGLE_APPLICATION_CREDENTIALS" environment variable.

        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(googleCloudPlatformProperties.getProjectId(), googleCloudPlatformProperties.getSubscriptionId());

        try {
            Subscriber subscriber = Subscriber.newBuilder(subscriptionName, newMailMessageReceiver).build();
            // TODO I don't think it should be executed this way.
            subscriber.startAsync().awaitRunning();
            subscriber.awaitTerminated();
        } catch (IllegalStateException exception) {
            LOGGER.error(ERROR_MESSAGE, exception);
            throw new RegistrantRuntimeException(ERROR_MESSAGE, exception);
        }
    }
}