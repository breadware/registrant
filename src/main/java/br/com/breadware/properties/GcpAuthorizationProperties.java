package br.com.breadware.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ConfigurationProperties(PropertiesPath.GCP_AUTHORIZATION)
@Validated
public class GcpAuthorizationProperties {

    @NotNull
    @Min(0)
    @Max(65535)
    private Integer localServerReceiverPort;

    @NotBlank
    private String authorizedUser;

    @NotBlank
    private String tokensDirectoryPath;

    @NotBlank
    private String credentialsFilePath;

    public String getCredentialsFilePath() {
        return credentialsFilePath;
    }

    public void setCredentialsFilePath(String credentialsFilePath) {
        this.credentialsFilePath = credentialsFilePath;
    }

    public Integer getLocalServerReceiverPort() {
        return localServerReceiverPort;
    }

    public void setLocalServerReceiverPort(Integer localServerReceiverPort) {
        this.localServerReceiverPort = localServerReceiverPort;
    }

    public String getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(String authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    public String getTokensDirectoryPath() {
        return tokensDirectoryPath;
    }

    public void setTokensDirectoryPath(String tokensDirectoryPath) {
        this.tokensDirectoryPath = tokensDirectoryPath;
    }
}