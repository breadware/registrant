package br.com.breadware.dao;

import br.com.breadware.exception.DataAccessException;
import br.com.breadware.model.message.ErrorMessage;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public abstract class AbstractFirestoreDao {

    private final CollectionReference collectionReference;

    public AbstractFirestoreDao(Firestore firestore) {
        this.collectionReference = firestore.collection(getDocumentName());
    }

    public void set(String id, Map<String, Object> data) throws DataAccessException {
        try {
            collectionReference.document(id)
                    .set(data)
                    .get();
        } catch (InterruptedException exception) {
            Thread.currentThread()
                    .interrupt();
            throw new DataAccessException(exception, ErrorMessage.ERROR_WHILE_SETTING_DATA, getDocumentName());
        } catch (ExecutionException exception) {
            throw new DataAccessException(exception, ErrorMessage.ERROR_WHILE_SETTING_DATA, getDocumentName());
        }
    }

    public Map<String, Object> get(String id) throws DataAccessException {
        try {
            return collectionReference.document(id)
                    .get()
                    .get()
                    .getData();
        } catch (InterruptedException exception) {
            Thread.currentThread()
                    .interrupt();
            throw new DataAccessException(exception, ErrorMessage.ERROR_WHILE_GETTING_DATA, getDocumentName());
        } catch (ExecutionException exception) {
            throw new DataAccessException(exception, ErrorMessage.ERROR_WHILE_GETTING_DATA, getDocumentName());
        }
    }

    public Collection<Map<String, Object>> getAll() throws DataAccessException {
        try {
            return collectionReference.get()
                    .get()
                    .getDocuments()
                    .stream()
                    .map(DocumentSnapshot::getData)
                    .collect(Collectors.toSet());
        } catch (InterruptedException exception) {
            Thread.currentThread()
                    .interrupt();
            throw new DataAccessException(exception, ErrorMessage.ERROR_WHILE_GETTING_DATA, getDocumentName());
        } catch (ExecutionException exception) {
            throw new DataAccessException(exception, ErrorMessage.ERROR_WHILE_GETTING_DATA, getDocumentName());
        }
    }

    protected abstract String getDocumentName();
}
