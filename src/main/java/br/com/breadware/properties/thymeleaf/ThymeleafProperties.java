package br.com.breadware.properties.thymeleaf;

import br.com.breadware.properties.PropertiesPath;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(PropertiesPath.THYMELEAF)
@Validated
public class ThymeleafProperties {

    private boolean cache = true;

    private String templatePrefix;

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public String getTemplatePrefix() {
        return templatePrefix;
    }

    public void setTemplatePrefix(String templatePrefix) {
        this.templatePrefix = templatePrefix;
    }
}
