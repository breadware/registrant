package br.com.breadware.model.message;

import java.io.File;

public enum LoggerMessage implements Message {

    SUBSCRIBER_CREATED(1),
    EMAIL_WATCH_EXPIRATION_TIME(2),
    SCHEDULING_NEXT_WATCH_REQUEST(3),
    SUBSCRIPTION_MESSAGE_RECEIVED(4),
    RETRIEVING_GMAIL_MESSAGE(5),
    GMAIL_HISTORY_RETRIEVAL_RETURNED_AN_EMPTY_LIST(6),
    EVENT_ID_RECEIVED_IS_PREVIOUS_TO_THE_LAST_ID(7),
    MESSAGE_BODY_IS_INVALID_JSON(8),
    MESSAGE_IS_NOT_ASSOCIATE_INFORMATION(9),
    GMAIL_MESSAGE_HAS_ALREADY_BEEN_HANDLED(10),
    GMAIL_MESSAGE_HAS_BEEN_HANDLED(11);

    public static final String FILE_PATH = "logger/logger".replace("/", File.separator);

    public static final String MESSAGE_PREFIX = "logger.";

    private final int value;

    LoggerMessage(int value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return MESSAGE_PREFIX + value;
    }
}
