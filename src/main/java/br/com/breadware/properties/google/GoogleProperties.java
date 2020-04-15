package br.com.breadware.properties.google;

import org.springframework.stereotype.Component;

@Component
public class GoogleProperties {

    private final GcpAuthorizationProperties gcpAuthorizationProperties;

    private final GcpPubSubProperties gcpPubSubProperties;

    private final GoogleCloudPlatformProperties googleCloudPlatformProperties;

    private final GoogleSheetsProperties googleSheetsProperties;

    public GoogleProperties(GcpAuthorizationProperties gcpAuthorizationProperties, GcpPubSubProperties gcpPubSubProperties, GoogleCloudPlatformProperties googleCloudPlatformProperties, GoogleSheetsProperties googleSheetsProperties) {
        this.gcpAuthorizationProperties = gcpAuthorizationProperties;
        this.gcpPubSubProperties = gcpPubSubProperties;
        this.googleCloudPlatformProperties = googleCloudPlatformProperties;
        this.googleSheetsProperties = googleSheetsProperties;
    }

    public GcpAuthorizationProperties getGcpAuthorizationProperties() {
        return gcpAuthorizationProperties;
    }

    public GcpPubSubProperties getGcpPubSubProperties() {
        return gcpPubSubProperties;
    }

    public GoogleCloudPlatformProperties getGoogleCloudPlatformProperties() {
        return googleCloudPlatformProperties;
    }

    public GoogleSheetsProperties getGoogleSheetsProperties() {
        return googleSheetsProperties;
    }
}
