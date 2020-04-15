package br.com.breadware.google.mail.watch;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.configuration.GcpConfiguration;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.properties.google.GoogleProperties;
import br.com.breadware.util.LoggerUtil;
import br.com.breadware.util.ZonedDateTimeUtil;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.WatchRequest;
import com.google.api.services.gmail.model.WatchResponse;
import com.google.pubsub.v1.ProjectTopicName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Component(BeanNames.GMAIL_WATCH_REQUESTER)
public class WatchRequester {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatchRequester.class);

    private static final List<String> LABEL_IDS = Collections.singletonList("INBOX");

    private final GoogleProperties googleProperties;

    private final WatchScheduler watchScheduler;

    private final ZonedDateTimeUtil zonedDateTimeUtil;

    private final LoggerUtil loggerUtil;

    private final Gmail gmail;

    @Inject
    public WatchRequester(GoogleProperties googleProperties, WatchScheduler watchScheduler, ZonedDateTimeUtil zonedDateTimeUtil, LoggerUtil loggerUtil, Gmail gmail) {
        this.googleProperties = googleProperties;
        this.watchScheduler = watchScheduler;
        this.zonedDateTimeUtil = zonedDateTimeUtil;
        this.loggerUtil = loggerUtil;
        this.gmail = gmail;
    }

    public void request() {

        final WatchRequest watchRequest = createWatchRequest();

        final WatchResponse watchResponse = sendWatchRequest(gmail, watchRequest);

        logWatchResponse(watchResponse);

        scheduleNextExecution(watchResponse);
    }

    private void scheduleNextExecution(WatchResponse watchResponse) {
        Instant expirationInstant = Instant.ofEpochMilli(watchResponse.getExpiration());

        watchScheduler.schedule(expirationInstant, this);
    }

    private void logWatchResponse(WatchResponse watchResponse) {
        Instant expirationInstant = Instant.ofEpochMilli(watchResponse.getExpiration());
        ZonedDateTime expirationZonedDateTime = zonedDateTimeUtil.convertFromUtcInstant(expirationInstant);
        loggerUtil.info(LOGGER, LoggerMessage.EMAIL_WATCH_EXPIRATION_TIME, zonedDateTimeUtil.writeAsHumanReadableFormat(expirationZonedDateTime));
    }

    private WatchResponse sendWatchRequest(Gmail gmail, WatchRequest watchRequest) {
        try {
            return gmail.users()
                    .watch(GcpConfiguration.USER_ID, watchRequest)
                    .execute();
        } catch (IOException exception) {
            loggerUtil.error(LOGGER, exception, ErrorMessage.ERROR_REQUESTING_WATCH);
            throw new RegistrantRuntimeException(exception, ErrorMessage.ERROR_REQUESTING_WATCH);
        }
    }


    private WatchRequest createWatchRequest() {

        String projectId = googleProperties.getGoogleCloudPlatformProperties()
                .getProjectId();

        String topicId = googleProperties.getGcpPubSubProperties()
                .getTopicId();

        @SuppressWarnings({"deprecation", "java:S1874"}) /* GCP Pub/sub API currently does not have an alternative for this deprecation (perhaps on next versions?). */
                String topicName = ProjectTopicName.format(projectId, topicId);

        return new WatchRequest()
                .setTopicName(topicName)
                .setLabelIds(LABEL_IDS);
    }
}
