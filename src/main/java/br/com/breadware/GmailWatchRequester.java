package br.com.breadware;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.properties.GoogleCloudPlatformProperties;
import br.com.breadware.util.LocalDateTimeUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component(BeanNames.GMAIL_WATCH_REQUESTER)
public class GmailWatchRequester {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailWatchRequester.class);

    private static final String APPLICATION_NAME = "registrant";

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final String ERROR_CREATING_NET_HTTP_TRANSPORT_MESSAGE = "Could not create net HTTP transport to SEND \"watch\" request.";

    private static final String ERROR_REQUESTING_WATCH = "Error while requesting to watch GMail inbox.";

    private static final String CREDENTIALS_FILE_PATH = "/Users/lamarceloleite2604/Documents/ThoughtWorks/Projetos/Breadware/Arquivos/credentials-registrant.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String USER_ENTERED = "USER_ENTERED";
    private static final String AUTHORIZATION_CODE_FLOW_ACCESS_TYPE = "offline";
    private static final String AUTHORIZATION_CODE_INSTALLED_APP_USER_ID = "me";
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/gmail.readonly");

    private final GoogleCloudPlatformProperties googleCloudPlatformProperties;

    private final LocalDateTimeUtil localDateTimeUtil;


    @Inject
    public GmailWatchRequester(GoogleCloudPlatformProperties googleCloudPlatformProperties, LocalDateTimeUtil localDateTimeUtil) {
        this.googleCloudPlatformProperties = googleCloudPlatformProperties;
        this.localDateTimeUtil = localDateTimeUtil;
    }

    @PostConstruct
    public void postConstruct() {
        request();
    }

    private void request() {
        final NetHttpTransport netHttpTransport = createNetHttpTransport();

        Credential credentials = getCredentials(netHttpTransport);

        Gmail gmail = new Gmail.Builder(netHttpTransport, JSON_FACTORY, credentials)
                .setApplicationName(APPLICATION_NAME)
                .build();

        String topicName = ProjectTopicName.format(googleCloudPlatformProperties.getProjectId(), googleCloudPlatformProperties.getTopicId());

        WatchRequest watchRequest = new WatchRequest();
        watchRequest.setTopicName(topicName).setLabelIds(Collections.singletonList("INBOX"));

        try {
            WatchResponse watchResponse = gmail.users().watch(AUTHORIZATION_CODE_INSTALLED_APP_USER_ID, watchRequest).execute();

            LocalDateTime expiration = localDateTimeUtil.convertFromEpochTime(watchResponse.getExpiration());
            LOGGER.info("Watch request will expire at {}.", localDateTimeUtil);
        } catch (IOException exception) {
            LOGGER.error(ERROR_REQUESTING_WATCH, exception);
            throw new RegistrantRuntimeException(ERROR_REQUESTING_WATCH, exception);
        }
    }

    private NetHttpTransport createNetHttpTransport() {
        try {
            return GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException exception) {
            LOGGER.error(ERROR_CREATING_NET_HTTP_TRANSPORT_MESSAGE, exception);
            throw new RegistrantRuntimeException(ERROR_CREATING_NET_HTTP_TRANSPORT_MESSAGE, exception);
        }
    }

    private GoogleClientSecrets retrieveGoogleClientSecrets() {
        try (InputStream inputStream = Files.newInputStream(Paths.get(CREDENTIALS_FILE_PATH)); InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            return GoogleClientSecrets.load(JSON_FACTORY, inputStreamReader);
        } catch (IOException e) {
            // TODO Show message and handle error.
            e.printStackTrace();
            return null;
        }
    }

    private Credential getCredentials(NetHttpTransport netHttpTransport)  {

        GoogleClientSecrets googleClientSecrets = retrieveGoogleClientSecrets();

        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = null;
        try {
            googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                    netHttpTransport, JSON_FACTORY, googleClientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType(AUTHORIZATION_CODE_FLOW_ACCESS_TYPE)
                    .build();


            LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(8888).build();
            return new AuthorizationCodeInstalledApp(googleAuthorizationCodeFlow, localServerReceiver).authorize(AUTHORIZATION_CODE_INSTALLED_APP_USER_ID);
        } catch (IOException e) {
            // TODO Show message and handle error.
            e.printStackTrace();
            return null;
        }
    }
}
