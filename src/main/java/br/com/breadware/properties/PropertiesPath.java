package br.com.breadware.properties;

public final class PropertiesPath {

    public static final String REGISTRANT = "registrant";

    public static final String MESSAGES = REGISTRANT + ".messages";

    public static final String GOOGLE_CLOUD_PLATFORM = REGISTRANT + ".google-cloud-platform";

    public static final String GCP_AUTHORIZATION = GOOGLE_CLOUD_PLATFORM + ".authorization";

    public static final String EMAIL = REGISTRANT + ".email";

    private PropertiesPath() {
        // Private constructor to avoid object instantiation.
    }
}
