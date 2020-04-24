package br.com.breadware.google.cloud.httprequestinitializer;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.configuration.GcpConfiguration;
import br.com.breadware.configuration.condition.NotRunningOnAppEngine;
import br.com.breadware.exception.AuthorizationRequestRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.properties.google.GcpAuthorizationProperties;
import br.com.breadware.util.EnvironmentUtil;
import br.com.breadware.util.EnvironmentVariables;
import br.com.breadware.util.MessageRetriever;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

@Component(BeanNames.HTTP_REQUEST_INITIALIZER_CREATOR)
@Conditional(NotRunningOnAppEngine.class)
public class AuthorizationRequester implements HttpRequestInitializerCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationRequester.class);

    private static final String AUTHORIZATION_CODE_FLOW_ACCESS_TYPE = "offline";

    private final NetHttpTransport netHttpTransport;
    private final GcpAuthorizationProperties gcpAuthorizationProperties;
    private final JsonFactory jsonFactory;
    private final MessageRetriever messageRetriever;
    private final EnvironmentUtil environmentUtil;

    @Inject
    public AuthorizationRequester(NetHttpTransport netHttpTransport, GcpAuthorizationProperties gcpAuthorizationProperties, JsonFactory jsonFactory, MessageRetriever messageRetriever, EnvironmentUtil environmentUtil) {
        this.netHttpTransport = netHttpTransport;
        this.gcpAuthorizationProperties = gcpAuthorizationProperties;
        this.jsonFactory = jsonFactory;
        this.messageRetriever = messageRetriever;
        this.environmentUtil = environmentUtil;
    }

    public Credential create() {
        GoogleClientSecrets googleClientSecrets = retrieveGoogleClientSecrets();
        return requestAuthorization(netHttpTransport, googleClientSecrets);
    }

    private GoogleClientSecrets retrieveGoogleClientSecrets() {

        Path clientIdFilePath = Path.of(environmentUtil.retrieveMandatoryVariable(EnvironmentVariables.CLIENT_ID_FILE_LOCATION));
        try (
                InputStream inputStream = Files.newInputStream(clientIdFilePath);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {

            return GoogleClientSecrets.load(jsonFactory, inputStreamReader);
        } catch (IOException exception) {
            throw logAndCreateAuthorizationRequestRuntimeException(ErrorMessage.ERROR_RETRIEVING_GOOGLE_CLIENT_SECRETS, exception);
        }
    }

    private Credential requestAuthorization(NetHttpTransport netHttpTransport, GoogleClientSecrets googleClientSecrets) {
        try {
            FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(gcpAuthorizationProperties.getTokensDirectoryPath()));
            GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                    netHttpTransport, jsonFactory, googleClientSecrets, GcpConfiguration.SCOPES)
                    .setDataStoreFactory(fileDataStoreFactory)
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