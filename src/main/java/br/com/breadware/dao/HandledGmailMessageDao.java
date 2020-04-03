package br.com.breadware.dao;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class HandledGmailMessageDao extends AbstractFirestoreDao {

    public static final String DOCUMENT_NAME = "handledGmailMessages";

    @Inject
    public HandledGmailMessageDao(Firestore firestore) {
        super(firestore);
    }

    @Override
    protected String getDocumentName() {
        return DOCUMENT_NAME;
    }
}
