package br.com.breadware.model.message;

import java.io.File;

public enum EmailMessage implements Message {
    NEW_ASSOCIATE_SUBJECT(1),
    EXISTING_ASSOCIATE_SUBJECT(2);

    public static final String FILE_PATH = "email/email".replace("/", File.separator);

    private static final String MESSAGE_PREFIX = "email.";

    private final int value;

    EmailMessage(int value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return MESSAGE_PREFIX + value;
    }
}
