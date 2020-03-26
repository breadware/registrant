package br.com.breadware.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(PropertiesPath.REGISTRANT)
@Validated
public class RegistrantProperties {

    private String locale = "en";

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
