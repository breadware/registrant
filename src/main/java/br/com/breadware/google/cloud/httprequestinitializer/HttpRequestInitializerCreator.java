package br.com.breadware.google.cloud.httprequestinitializer;

import com.google.api.client.http.HttpRequestInitializer;

public interface HttpRequestInitializerCreator {

  HttpRequestInitializer create();
}
