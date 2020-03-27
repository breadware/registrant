package br.com.breadware.model.message;

import java.io.File;

public enum ErrorMessage implements Message {

    ERROR_RETRIEVING_GOOGLE_CLIENT_SECRETS(1),
    ERROR_REQUESTING_AUTHORIZATION(2),
    ERROR_REQUESTING_WATCH(3);

    public static final String FILE_PATH = "error/error".replace("/", File.separator);

    public static final String MESSAGE_PREFIX = "error.";

    private int value;

    ErrorMessage(int value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return MESSAGE_PREFIX + value;
    }
}
