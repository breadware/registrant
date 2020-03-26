package br.com.breadware.configuration;

import br.com.breadware.properties.RegistrantProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class RegistrantConfiguration {

    @Bean(BeanNames.LOCALE)
    public Locale createLocale(RegistrantProperties registrantProperties) {
        return Locale.forLanguageTag(registrantProperties.getLocale());
    }
}
