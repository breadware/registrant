package br.com.breadware.google.cloud.httprequestinitializer.datastore;

import br.com.breadware.bo.CloudStorageBo;
import br.com.breadware.model.CloudStorageLocation;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;

import java.io.IOException;
import java.io.Serializable;

public class CloudStorageDataStoreFactory implements DataStoreFactory {

    private final CloudStorageBo cloudStorageBo;
    private final CloudStorageLocation cloudStorageLocation;

    public CloudStorageDataStoreFactory(CloudStorageBo cloudStorageBo, CloudStorageLocation cloudStorageLocation) {
        this.cloudStorageBo = cloudStorageBo;
        this.cloudStorageLocation = cloudStorageLocation;
    }

    @Override
    public <V extends Serializable> DataStore<V> getDataStore(String id) throws IOException {
        return new CloudStorageDataStore<V>(this, id, cloudStorageBo, cloudStorageLocation);
    }
}
