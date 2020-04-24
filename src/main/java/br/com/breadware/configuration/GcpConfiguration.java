package br.com.breadware.configuration;

import br.com.breadware.google.cloud.httprequestinitializer.HttpRequestInitializerCreator;
import br.com.breadware.properties.google.GoogleCloudPlatformProperties;
import br.com.breadware.util.EnvironmentUtil;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Builder;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GcpConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(GcpConfiguration.class);

  public static final String USER_ID = "me";

  public static final String CREDENTIALS_ENVIRONMENT_VARIABLE_NAME = "GOOGLE_APPLICATION_CREDENTIALS";

  private static final String APPLICATION_NAME = "registrant";

  public static final List<String> SCOPES = List
      .of(GmailScopes.GMAIL_READONLY, SheetsScopes.SPREADSHEETS);

  @Bean(BeanNames.NET_HTTP_TRANSPORT)
  public NetHttpTransport createNetHttpTransport() throws GeneralSecurityException, IOException {
    return GoogleNetHttpTransport.newTrustedTransport();
  }

  @Bean(BeanNames.JSON_FACTORY)
  public JsonFactory createJsonFactory() {
    return JacksonFactory.getDefaultInstance();
  }

  @Bean(BeanNames.HTTP_REQUEST_INITIALIZER)
  public HttpRequestInitializer createHttpRequestInitializer(
      HttpRequestInitializerCreator httpRequestInitializerCreator) {
    LOGGER.info("Using \"{}\" class as HTTP request initializer creator.",
        httpRequestInitializerCreator.getClass().getName());
    return httpRequestInitializerCreator.create();
  }

  @Bean(BeanNames.GMAIL)
  public Gmail createGmail(NetHttpTransport netHttpTransport, JsonFactory jsonFactory,
      HttpRequestInitializer httpRequestInitializer) {
    return new Gmail.Builder(netHttpTransport, jsonFactory, httpRequestInitializer)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }

  @Bean(BeanNames.FIRESTORE)
  public Firestore createFirestore(GoogleCloudPlatformProperties googleCloudPlatformProperties,
      EnvironmentUtil environmentUtil) {

    FirestoreOptions firestoreOptions = null;
    if (environmentUtil.isAppEngine()) {
      GoogleCredentials credentials = ComputeEngineCredentials.create();
      firestoreOptions = FirestoreOptions.newBuilder().setCredentials(credentials)
          .setProjectId(googleCloudPlatformProperties.getProjectId()).build();
    } else {
      firestoreOptions = FirestoreOptions.getDefaultInstance()
          .toBuilder()
          .setProjectId(googleCloudPlatformProperties.getProjectId())
          .build();
    }

    return firestoreOptions.getService();
  }

  @Bean(BeanNames.SHEETS)
  public Sheets createSheets(NetHttpTransport netHttpTransport, JsonFactory jsonFactory,
      HttpRequestInitializer httpRequestInitializer) {
    return new Sheets.Builder(netHttpTransport, jsonFactory, httpRequestInitializer)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }
}
