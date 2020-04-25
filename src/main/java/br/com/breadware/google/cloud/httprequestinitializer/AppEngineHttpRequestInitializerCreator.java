package br.com.breadware.google.cloud.httprequestinitializer;

import br.com.breadware.configuration.BeanNames;
import br.com.breadware.configuration.GcpConfiguration;
import br.com.breadware.configuration.condition.RunningOnAppEngine;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.http.HttpRequestInitializer;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

// @Component(BeanNames.HTTP_REQUEST_INITIALIZER_CREATOR)
// @Conditional(RunningOnAppEngine.class)
public class AppEngineHttpRequestInitializerCreator implements HttpRequestInitializerCreator {

    @Override
    public HttpRequestInitializer create() {
        return new AppIdentityCredential(GcpConfiguration.SCOPES);
    }
}
