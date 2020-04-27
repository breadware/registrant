package br.com.breadware.google.cloud.authorization;

import br.com.breadware.configuration.GcpConfiguration;
import br.com.breadware.exception.AuthorizationRequestRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.properties.google.GcpAuthorizationProperties;
import br.com.breadware.util.MessageRetriever;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class AuthorizationRequester {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationRequester.class);
    private static final String AUTHORIZATION_CODE_FLOW_ACCESS_TYPE = "offline";

    private final NetHttpTransport netHttpTransport;
    private final GcpAuthorizationProperties gcpAuthorizationProperties;
    private final JsonFactory jsonFactory;
    private final MessageRetriever messageRetriever;
    private final ClientIdRetriever clientIdRetriever;
    private final DataStoreFactory dataStoreFactory;


    @Inject
    public AuthorizationRequester(NetHttpTransport netHttpTransport, GcpAuthorizationProperties gcpAuthorizationProperties, JsonFactory jsonFactory, MessageRetriever messageRetriever, ClientIdRetriever clientIdRetriever, ClientIdRetriever clientIdRetriever1, DataStoreFactory dataStoreFactory) {
        this.netHttpTransport = netHttpTransport;
        this.gcpAuthorizationProperties = gcpAuthorizationProperties;
        this.jsonFactory = jsonFactory;
        this.messageRetriever = messageRetriever;
        this.clientIdRetriever = clientIdRetriever1;
        this.dataStoreFactory = dataStoreFactory;
    }

    public Credential create() {
        GoogleClientSecrets googleClientSecrets = retrieveGoogleClientSecrets();
        return requestAuthorization(netHttpTransport, googleClientSecrets);
    }

    private GoogleClientSecrets retrieveGoogleClientSecrets() {

        try (InputStreamReader inputStreamReader = clientIdRetriever.retrieve()) {
            return GoogleClientSecrets.load(jsonFactory, inputStreamReader);
        } catch (IOException exception) {
            throw logAndCreateAuthorizationRequestRuntimeException(ErrorMessage.ERROR_RETRIEVING_GOOGLE_CLIENT_SECRETS, exception);
        }
    }

    private Credential requestAuthorization(NetHttpTransport netHttpTransport, GoogleClientSecrets googleClientSecrets) {
        try {
            GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                    netHttpTransport, jsonFactory, googleClientSecrets, GcpConfiguration.SCOPES)
                    .setDataStoreFactory(dataStoreFactory)
                    .setAccessType(AUTHORIZATION_CODE_FLOW_ACCESS_TYPE)
                    .build();

            LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder()
                    .setPort(gcpAuthorizationProperties.getLocalServerReceiverPort())
                    .build();

            return new AuthorizationCodeInstalledApp(googleAuthorizationCodeFlow, localServerReceiver).authorize(gcpAuthorizationProperties.getAuthorizedUser());
        } catch (IOException exception) {
            throw logAndCreateAuthorizationRequestRuntimeException(ErrorMessage.ERROR_REQUESTING_AUTHORIZATION, exception);
        }
    }

    private AuthorizationRequestRuntimeException logAndCreateAuthorizationRequestRuntimeException(ErrorMessage errorMessage, IOException exception) {
        LOGGER.error(messageRetriever.getMessage(errorMessage), exception);
        return new AuthorizationRequestRuntimeException(errorMessage, exception);
    }
}
