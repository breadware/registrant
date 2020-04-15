package br.com.breadware.google;

import br.com.breadware.exception.AuthorizationRequestRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.properties.google.GcpAuthorizationProperties;
import br.com.breadware.util.EnvironmentVariableUtil;
import br.com.breadware.util.MessageRetriever;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class AuthorizationRequester {

    public static final String CLIENT_ID_FILE_LOCATION_ENVIRONMENT_VARIABLE = "GOOGLE_CLIENT_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationRequester.class);
    private static final List<String> SCOPES = List.of(GmailScopes.GMAIL_READONLY, SheetsScopes.SPREADSHEETS);
    private static final String AUTHORIZATION_CODE_FLOW_ACCESS_TYPE = "offline";

    private final NetHttpTransport netHttpTransport;
    private final GcpAuthorizationProperties gcpAuthorizationProperties;
    private final JsonFactory jsonFactory;
    private final MessageRetriever messageRetriever;
    private final EnvironmentVariableUtil environmentVariableUtil;

    @Inject
    public AuthorizationRequester(NetHttpTransport netHttpTransport, GcpAuthorizationProperties gcpAuthorizationProperties, JsonFactory jsonFactory, MessageRetriever messageRetriever, EnvironmentVariableUtil environmentVariableUtil) {
        this.netHttpTransport = netHttpTransport;
        this.gcpAuthorizationProperties = gcpAuthorizationProperties;
        this.jsonFactory = jsonFactory;
        this.messageRetriever = messageRetriever;
        this.environmentVariableUtil = environmentVariableUtil;
    }

    public Credential getCredential() {
        GoogleClientSecrets googleClientSecrets = retrieveGoogleClientSecrets();
        return requestAuthorization(netHttpTransport, googleClientSecrets);
    }

    private GoogleClientSecrets retrieveGoogleClientSecrets() {
        environmentVariableUtil.throwExceptionIfDoesNotExist(CLIENT_ID_FILE_LOCATION_ENVIRONMENT_VARIABLE);
        Path clientIdFilePath = Path.of(System.getenv(CLIENT_ID_FILE_LOCATION_ENVIRONMENT_VARIABLE));
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
                    netHttpTransport, jsonFactory, googleClientSecrets, SCOPES)
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
