package br.com.breadware.properties;

public final class PropertiesPath {

    public static final String REGISTRANT = "registrant";

    public static final String MESSAGES = REGISTRANT + ".messages";

    public static final String EMAIL = REGISTRANT + ".email";

    public static final String GOOGLE = REGISTRANT + ".google";

    public static final String GOOGLE_CLOUD_PLATFORM = GOOGLE + ".cloud-platform";

    public static final String GCP_PUB_SUB = GOOGLE_CLOUD_PLATFORM + ".pub-sub";

    public static final String GCP_AUTHORIZATION = GOOGLE_CLOUD_PLATFORM + ".authorization";

    public static final String GOOGLE_SHEETS = GOOGLE + ".sheets";

    public static final String THYMELEAF = REGISTRANT + ".thymeleaf";

    public static final String THYMELEAF_TEMPLATE_RESOLVERS = THYMELEAF + ".template-resolvers";

    public static final String RECEIVER_BANK_ACCOUNT = REGISTRANT + ".receiver-bank-account";

    private PropertiesPath() {
        // Private constructor to avoid object instantiation.
    }
}
