package br.com.breadware.model.message;

import java.io.File;

public enum ErrorMessage implements Message {

    ERROR_RETRIEVING_GOOGLE_CLIENT_SECRETS(1),
    ERROR_REQUESTING_AUTHORIZATION(2),
    ERROR_REQUESTING_WATCH(3),
    ERROR_CREATING_SUBSCRIBER(4),
    UNDEFINED_ENVIRONMENT_VARIABLE(5),
    ERROR_WHILE_MAPPING(6),
    ERROR_WHILE_REQUESTING_GMAIL_MESSAGE(7),
    ERROR_WHILE_HANDLING_MESSAGE(8),
    ERROR_WHILE_REQUESTING_GMAIL_HISTORY(9),
    ERROR_RETRIEVING_MIME_MESSAGE_CONTENT_AS_TEXT(10),
    ERROR_WHILE_SETTING_DATA(11),
    ERROR_WHILE_GETTING_DATA(12),
    ERROR_UPDATING_LAST_HISTORY_EVENT(13),
    ERROR_WHILE_RETRIEVING_SPREADSHEET(14),
    ERROR_WHILE_APPENDING_VALUE_ON_SPREADSHEET(15),
    ERROR_WHILE_CHECKING_ASSOCIATE_ALREADY_EXISTS(16),
    ERROR_WHILE_VALIDATING_MESSAGE(17),
    INVALID_MESSAGE_ANALYSIS_STATUS_RESULT(18);

    public static final String FILE_PATH = "error/error".replace("/", File.separator);

    public static final String MESSAGE_PREFIX = "error.";

    private final int value;

    ErrorMessage(int value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return MESSAGE_PREFIX + value;
    }
}
