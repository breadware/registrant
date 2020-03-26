package br.com.breadware;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.properties.GoogleCloudPlatformProperties;
import br.com.breadware.util.LocalDateTimeUtil;
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component(BeanNames.GMAIL_WATCH_REQUESTER)
public class GmailWatchRequester {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailWatchRequester.class);

    private static final String APPLICATION_NAME = "registrant";

    private static final String ERROR_REQUESTING_WATCH = "Error while requesting to watch GMail inbox.";

    private static final List<String> LABEL_IDS = Collections.singletonList("INBOX");
    private static final String DEFAULT_USER_ID_ON_WATCH = "me";


    private final GoogleCloudPlatformProperties googleCloudPlatformProperties;

    private final LocalDateTimeUtil localDateTimeUtil;

    private final NetHttpTransport netHttpTransport;

    private final Credential credential;

    private final JsonFactory jsonFactory;

    @Inject
    public GmailWatchRequester(GoogleCloudPlatformProperties googleCloudPlatformProperties, LocalDateTimeUtil localDateTimeUtil, NetHttpTransport netHttpTransport, Credential credential, JsonFactory jsonFactory) {
        this.googleCloudPlatformProperties = googleCloudPlatformProperties;
        this.localDateTimeUtil = localDateTimeUtil;
        this.netHttpTransport = netHttpTransport;
        this.credential = credential;
        this.jsonFactory = jsonFactory;
    }

    @PostConstruct
    public void postConstruct() {
        requestInboxWatch();
    }

    private void requestInboxWatch() {

        final Gmail gmail = createGmailObject();

        final WatchRequest watchRequest = createWatchRequest();

        final WatchResponse watchResponse = sendWatchRequest(gmail, watchRequest);

        LocalDateTime expiration = localDateTimeUtil.convertFromEpochTime(watchResponse.getExpiration());
        LOGGER.info("Watch request will expire at {}.", expiration);
    }

    private WatchResponse sendWatchRequest(Gmail gmail, WatchRequest watchRequest) {
        try {
            return gmail.users().watch(DEFAULT_USER_ID_ON_WATCH, watchRequest).execute();
        } catch (IOException exception) {
            LOGGER.error(ERROR_REQUESTING_WATCH, exception);
            throw new RegistrantRuntimeException(ERROR_REQUESTING_WATCH, exception);
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
