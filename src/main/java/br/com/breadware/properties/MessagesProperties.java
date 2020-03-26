package br.com.breadware.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(PropertiesPath.MESSAGES)
public class MessagesProperties {

    private String fileBaseDirectoryPath;

    public String getFileBaseDirectoryPath() {
        return fileBaseDirectoryPath;
    }

    public void setFileBaseDirectoryPath(String fileBaseDirectoryPath) {
        this.fileBaseDirectoryPath = fileBaseDirectoryPath;
    }
}
