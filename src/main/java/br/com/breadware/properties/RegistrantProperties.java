package br.com.breadware.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(PropertiesPath.REGISTRANT)
@Validated
public class RegistrantProperties {

    private String locale = "en";

    private String zoneId = "UTC";

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
}
