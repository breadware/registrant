package br.com.breadware.model;

import br.com.breadware.model.message.EmailMessage;

public enum Email {

    NEW_ASSOCIATE(ThymeleafTemplates.NEW_ASSOCIATE_EMAIL, EmailMessage.NEW_ASSOCIATE_SUBJECT),
    EXISTING_ASSOCIATE(ThymeleafTemplates.EXISTING_ASSOCIATE_EMAIL, EmailMessage.EXISTING_ASSOCIATE_SUBJECT);

    private final ThymeleafTemplates thymeleafTemplates;
    private final EmailMessage subjectMessage;

    Email(ThymeleafTemplates thymeleafTemplates, EmailMessage subjectMessage) {
        this.thymeleafTemplates = thymeleafTemplates;
        this.subjectMessage = subjectMessage;
    }

    public ThymeleafTemplates getThymeleafTemplates() {
        return thymeleafTemplates;
    }

    public EmailMessage getSubjectMessage() {
        return subjectMessage;
    }
}
