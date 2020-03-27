package br.com.breadware.configuration;

import br.com.breadware.watch.AuthorizationRequester;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GcpConfiguration {

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


}
