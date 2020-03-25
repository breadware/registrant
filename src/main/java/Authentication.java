import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Authentication {

    // private static final String CREDENTIALS_FILE_PATH = "src/main/resources/google-cloud-platform/tutorials-credentials.json";

    private static final List<String> SCOPES = Collections.singletonList("");

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final String AUTHORIZATION_CODE_FLOW_ACCESS_TYPE = "offline";

    private static final String AUTHORIZATION_CODE_INSTALLED_APP_USER_ID = "user";

//    private static GoogleClientSecrets retrieveGoogleClientSecrets() throws IOException {
//        try (InputStream inputStream = Files.newInputStream(Paths.get(CREDENTIALS_FILE_PATH)); InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
//            return GoogleClientSecrets.load(JSON_FACTORY, inputStreamReader);
//        }
//    }

//    private Credential getCredentials(NetHttpTransport netHttpTransport) throws IOException {
//
//        // GoogleClientSecrets googleClientSecrets = retrieveGoogleClientSecrets();
//
//        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
//                netHttpTransport, JSON_FACTORY, googleClientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType(AUTHORIZATION_CODE_FLOW_ACCESS_TYPE)
//                .build();
//
//        LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(googleAuthorizationCodeFlow, localServerReceiver).authorize(AUTHORIZATION_CODE_INSTALLED_APP_USER_ID);
//    }

//    public void authenticate() {
//    }
}
