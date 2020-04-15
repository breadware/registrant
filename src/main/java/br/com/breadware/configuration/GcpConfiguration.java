package br.com.breadware.configuration;

import br.com.breadware.properties.google.GoogleCloudPlatformProperties;
import br.com.breadware.util.EnvironmentVariableUtil;
import br.com.breadware.google.AuthorizationRequester;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.sheets.v4.Sheets;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GcpConfiguration {

    public static final String USER_ID = "me";
    public static final String CREDENTIALS_ENVIRONMENT_VARIABLE_NAME = "GOOGLE_APPLICATION_CREDENTIALS";
    private static final String APPLICATION_NAME = "registrant";

    @Bean(BeanNames.NET_HTTP_TRANSPORT)
    public NetHttpTransport createNetHttpTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean(BeanNames.JSON_FACTORY)
    public JsonFactory createJsonFactory() {
        return JacksonFactory.getDefaultInstance();
    }

    @Bean(BeanNames.CREDENTIAL)
    public Credential createCredentials(AuthorizationRequester authorizationRequester) {
        return authorizationRequester.getCredential();
    }

    @Bean(BeanNames.GMAIL)
    public Gmail createGmail(NetHttpTransport netHttpTransport, JsonFactory jsonFactory, Credential credential) {
        return new Gmail.Builder(netHttpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Bean(BeanNames.FIRESTORE)
    public Firestore createFirestore(GoogleCloudPlatformProperties googleCloudPlatformProperties, EnvironmentVariableUtil environmentVariableUtil) {

        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance()
                .toBuilder()
                .setProjectId(googleCloudPlatformProperties.getProjectId())
                .build();

        return firestoreOptions.getService();
    }

    @Bean(BeanNames.SHEETS)
    public Sheets createSheets(NetHttpTransport netHttpTransport, JsonFactory jsonFactory, Credential credential) {
        return new Sheets.Builder(netHttpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
