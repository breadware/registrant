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
    ERROR_WHILE_HANDLING_SUBSCRIPTION_MESSAGE(8),
    ERROR_WHILE_RETRIEVING_GMAIL_HISTORY(9),
    ERROR_RETRIEVING_MIME_MESSAGE_CONTENT_AS_TEXT(10),
    ERROR_WHILE_SETTING_DATA(11),
    ERROR_WHILE_GETTING_DATA(12),
    ERROR_WHILE_RETRIEVING_SPREADSHEET(13),
    ERROR_WHILE_CHECKING_ASSOCIATE_ALREADY_EXISTS(14),
    ERROR_WHILE_VALIDATING_MESSAGE(15),
    INVALID_MESSAGE_ANALYSIS_STATUS_RESULT(16),
    COULD_NOT_FIND_MESSAGE_CONTENT(17),
    ERROR_EXTRACTING_ASSOCIATE_INFORMATION(18),
    ERROR_MAPPING_OBJECT(19),
    ERROR_CLONING_OBJECT(20),
    WRAPPED_OBJECT_IS_NOT_OF_EXPECTED_TYPE(21),
    ERROR_WHILE_UPDATING_VALUES_ON_SPREADSHEET(22),
    INVALID_SHEETS_RANGE(23),
    ERROR_WHILE_HANDLING_GMAIL_MESSAGE(24),
    ERROR_CREATING_MIME_MULTIPART_OBJECT(25),
    ERROR_COMPOSING_GMAIL_MESSAGE(26),
    ERROR_SENDING_MESSAGE_THROUGH_GMAIL(27),
    TEXT_IS_NOT_CLOUD_STORAGE_URI(28),
    ERROR_RETRIEVING_GOOGLE_CLIENT_ID(29),
    COULD_NOT_LOCATE_CLIENT_ID_FILE(30),
    OBJECT_ON_CLOUD_STORAGE_IS_NOT_HASHMAP(31),
    ERROR_RETRIEVING_DATA_MAP_FROM_CLOUD_STORAGE(32),
    ERROR_MODIFYING_GMAIL_MESSAGE(33),
    UNDEFINED_LAST_HISTORY_EVENT_ID(34),
    ERROR_EXCLUDING_EXPIRED_HANDLED_MESSAGES(35),
    ERROR_WHILE_DELETING_DATA(36);

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
