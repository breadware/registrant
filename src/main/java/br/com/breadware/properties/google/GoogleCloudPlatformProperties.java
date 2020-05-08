package br.com.breadware.properties.google;

import br.com.breadware.properties.PropertiesPath;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties(PropertiesPath.GOOGLE_CLOUD_PLATFORM)
@Validated
public class GoogleCloudPlatformProperties {

    @NotBlank
    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
