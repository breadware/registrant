package br.com.breadware.properties.thymeleaf;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class ThymeleafTemplateResolverProperties {

    @NotBlank
    private String prefix;

    @NotEmpty
    private Set<String> resolvablePatterns;

    @NotBlank
    private String mode;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Set<String> getResolvablePatterns() {
        return resolvablePatterns;
    }

    public void setResolvablePatterns(Set<String> resolvablePatterns) {
        this.resolvablePatterns = resolvablePatterns;
    }
}
