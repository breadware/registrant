package br.com.breadware.dao;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class LastHistoryEventDao extends AbstractFirestoreDao {

    public static final String DOCUMENT_NAME = "lastHistoryEvent";

    @Inject
    public LastHistoryEventDao(Firestore firestore) {
        super(firestore);
    }

    @Override
    protected String getDocumentName() {
        return DOCUMENT_NAME;
    }
}
