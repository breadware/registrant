package br.com.breadware.model.message;

import java.io.File;

public enum LoggerMessage implements Message{

    SUBSCRIBER_CREATED(1),
    EMAIL_WATCH_EXPIRATION_TIME(2),
    SCHEDULING_NEXT_WATCH_REQUEST(3);

    public static final String FILE_PATH = "logger/logger".replace("/",File.separator);

    public static final String MESSAGE_PREFIX = "logger.";

    private int value;

    LoggerMessage(int value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return MESSAGE_PREFIX + value;
    }
}
