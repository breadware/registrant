package br.com.breadware.configuration;

import br.com.breadware.google.cloud.authorization.AuthorizationRequester;
import br.com.breadware.properties.google.GoogleCloudPlatformProperties;
import br.com.breadware.util.EnvironmentUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class GcpConfiguration {

    public static final String USER_ID = "me";

    public static final List<String> SCOPES = List
            .of(GmailScopes.GMAIL_READONLY, GmailScopes.GMAIL_COMPOSE, SheetsScopes.SPREADSHEETS);

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
    public Credential createCredential(AuthorizationRequester authorizationRequester) {
        return authorizationRequester.create();
    }

    @Bean(BeanNames.GMAIL)
    public Gmail createGmail(NetHttpTransport netHttpTransport, JsonFactory jsonFactory,
                             HttpRequestInitializer httpRequestInitializer, EnvironmentUtil environmentUtil) {

        environmentUtil.checkGoogleApplicationCredentialsEnvironmentVariable();
        return new Gmail.Builder(netHttpTransport, jsonFactory, httpRequestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Bean(BeanNames.FIRESTORE)
    public Firestore createFirestore(GoogleCloudPlatformProperties googleCloudPlatformProperties,
                                     EnvironmentUtil environmentUtil) {
        environmentUtil.checkGoogleApplicationCredentialsEnvironmentVariable();
        return FirestoreOptions.getDefaultInstance()
                .toBuilder()
                .setProjectId(googleCloudPlatformProperties.getProjectId())
                .build()
                .getService();
    }

    @Bean(BeanNames.STORAGE)
    public Storage createStorage(EnvironmentUtil environmentUtil) {
        environmentUtil.checkGoogleApplicationCredentialsEnvironmentVariable();
        return StorageOptions.getDefaultInstance()
                .getService();
    }

    @Bean(BeanNames.SHEETS)
    public Sheets createSheets(NetHttpTransport netHttpTransport, JsonFactory jsonFactory,
                               HttpRequestInitializer httpRequestInitializer, EnvironmentUtil environmentUtil) {
        environmentUtil.checkGoogleApplicationCredentialsEnvironmentVariable();
        return new Sheets.Builder(netHttpTransport, jsonFactory, httpRequestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
