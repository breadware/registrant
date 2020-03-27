package br.com.breadware.watch;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.properties.GoogleCloudPlatformProperties;
import br.com.breadware.util.LoggerUtil;
import br.com.breadware.util.ZonedDateTimeUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
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
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Component(BeanNames.GMAIL_WATCH_REQUESTER)
public class WatchRequester {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatchRequester.class);

    private static final String APPLICATION_NAME = "registrant";

    private static final List<String> LABEL_IDS = Collections.singletonList("INBOX");

    private static final String DEFAULT_USER_ID_ON_WATCH = "me";

    private final GoogleCloudPlatformProperties googleCloudPlatformProperties;

    private final NetHttpTransport netHttpTransport;

    private final Credential credential;

    private final JsonFactory jsonFactory;

    private final WatchScheduler watchScheduler;

    private final ZonedDateTimeUtil zonedDateTimeUtil;

    private final LoggerUtil loggerUtil;

    @Inject
    public WatchRequester(GoogleCloudPlatformProperties googleCloudPlatformProperties, NetHttpTransport netHttpTransport, Credential credential, JsonFactory jsonFactory, WatchScheduler watchScheduler, ZonedDateTimeUtil zonedDateTimeUtil, LoggerUtil loggerUtil) {
        this.googleCloudPlatformProperties = googleCloudPlatformProperties;
        this.netHttpTransport = netHttpTransport;
        this.credential = credential;
        this.jsonFactory = jsonFactory;
        this.watchScheduler = watchScheduler;
        this.zonedDateTimeUtil = zonedDateTimeUtil;
        this.loggerUtil = loggerUtil;
    }

    public WatchResponse request() {

        final Gmail gmail = createGmailObject();

        final WatchRequest watchRequest = createWatchRequest();

        final WatchResponse watchResponse = sendWatchRequest(gmail, watchRequest);

        logWatchResponse(watchResponse);

        scheduleNextExecution(watchResponse);

        return watchResponse;
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
            return gmail.users().watch(DEFAULT_USER_ID_ON_WATCH, watchRequest).execute();
        } catch (IOException exception) {
            loggerUtil.error(LOGGER, exception, ErrorMessage.ERROR_REQUESTING_WATCH);
            throw new RegistrantRuntimeException(exception, ErrorMessage.ERROR_REQUESTING_WATCH);
        }
    }

    private WatchRequest createWatchRequest() {
        String topicName = ProjectTopicName.format(googleCloudPlatformProperties.getProjectId(), googleCloudPlatformProperties.getTopicId());

        return new WatchRequest()
                .setTopicName(topicName)
                .setLabelIds(LABEL_IDS);
    }

    private Gmail createGmailObject() {
        return new Gmail.Builder(netHttpTransport, jsonFactory, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
    }
}
