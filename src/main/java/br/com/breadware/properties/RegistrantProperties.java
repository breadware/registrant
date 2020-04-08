package br.com.breadware.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties(PropertiesPath.REGISTRANT)
@Validated
public class RegistrantProperties {

    private String locale = "en";

    private String zoneId = "UTC";

    @NotBlank
    private String emailVerificationUuid;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getEmailVerificationUuid() {
        return emailVerificationUuid;
    }

    public void setEmailVerificationUuid(String emailVerificationUuid) {
        this.emailVerificationUuid = emailVerificationUuid;
    }
}
