package br.com.breadware.google.cloud.pubsub;

import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.properties.google.GcpPubSubProperties;
import br.com.breadware.properties.google.GoogleCloudPlatformProperties;
import br.com.breadware.util.LoggerUtil;
import com.google.api.gax.core.ExecutorProvider;
import com.google.api.gax.core.InstantiatingExecutorProvider;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class GmailInboxHistoryEventSubscriberCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailInboxHistoryEventSubscriberCreator.class);

    private final GoogleCloudPlatformProperties googleCloudPlatformProperties;

    private final GcpPubSubProperties gcpPubSubProperties;

    private final GmailInboxHistoryEventMessageReceiver gmailInboxHistoryEventMessageReceiver;

    private final LoggerUtil loggerUtil;

    @Inject
    public GmailInboxHistoryEventSubscriberCreator(GoogleCloudPlatformProperties googleCloudPlatformProperties, GcpPubSubProperties gcpPubSubProperties, GmailInboxHistoryEventMessageReceiver gmailInboxHistoryEventMessageReceiver, LoggerUtil loggerUtil) {
        this.googleCloudPlatformProperties = googleCloudPlatformProperties;
        this.gcpPubSubProperties = gcpPubSubProperties;
        this.gmailInboxHistoryEventMessageReceiver = gmailInboxHistoryEventMessageReceiver;
        this.loggerUtil = loggerUtil;
    }

    public void createAndStart() {

        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(googleCloudPlatformProperties.getProjectId(), gcpPubSubProperties.getSubscriptionId());

        try {

            ExecutorProvider executorProvider =
                    InstantiatingExecutorProvider.newBuilder()
                            .setExecutorThreadCount(1)
                            .build();

            Subscriber subscriber = Subscriber.newBuilder(subscriptionName, gmailInboxHistoryEventMessageReceiver)
                    .setExecutorProvider(executorProvider)
                    .build();

            subscriber.startAsync();
            loggerUtil.info(LOGGER, LoggerMessage.SUBSCRIBER_CREATED);
        } catch (IllegalStateException exception) {
            loggerUtil.error(LOGGER, exception, ErrorMessage.ERROR_CREATING_SUBSCRIBER);
            throw new RegistrantRuntimeException(ErrorMessage.ERROR_CREATING_SUBSCRIBER, exception);
        }
    }
}