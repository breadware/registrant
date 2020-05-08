package br.com.breadware.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ConfigurationProperties(PropertiesPath.MESSAGES)
public class MessagesProperties {

    private String fileBaseDirectoryPath;

    @NotNull
    @Positive
    private Double handledIdsRetentionTimeDays;

    public String getFileBaseDirectoryPath() {
        return fileBaseDirectoryPath;
    }

    public void setFileBaseDirectoryPath(String fileBaseDirectoryPath) {
        this.fileBaseDirectoryPath = fileBaseDirectoryPath;
    }

    public Double getHandledIdsRetentionTimeDays() {
        return handledIdsRetentionTimeDays;
    }

    public void setHandledIdsRetentionTimeDays(Double handledIdsRetentionTimeDays) {
        this.handledIdsRetentionTimeDays = handledIdsRetentionTimeDays;
    }
}
