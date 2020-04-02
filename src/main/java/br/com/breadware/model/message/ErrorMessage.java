package br.com.breadware.model.message;

import java.io.File;

public enum ErrorMessage implements Message {

    ERROR_RETRIEVING_GOOGLE_CLIENT_SECRETS(1),
    ERROR_REQUESTING_AUTHORIZATION(2),
    ERROR_REQUESTING_WATCH(3),
    ERROR_CREATING_SUBSCRIBER(4),
    UNDEFINED_ENVIRONMENT_VARIABLE(5),
    ERROR_MAPPING_SUBSCRIPTION_MESSAGE(6),
    ERROR_WHILE_REQUESTING_GMAIL_MESSAGE(7),
    ERROR_WHILE_HANDLING_MESSAGE(8),
    ERROR_WHILE_REQUESTING_GMAIL_HISTORY(9),
    GMAIL_HISTORY_RETRIEVAL_RETURNED_NO_RESULTS(10),
    GMAIL_HISTORY_RETRIEVAL_DID_NOT_RETURNED_EXPECTED_ID(11);

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
