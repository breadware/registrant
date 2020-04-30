package br.com.breadware.google.cloud.authorization.datastore;

import br.com.breadware.bo.CloudStorageBo;
import br.com.breadware.model.CloudStorageLocation;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class DataStoreFactoryCreator {

    private final CloudStorageBo cloudStorageBo;

    public DataStoreFactoryCreator(CloudStorageBo cloudStorageBo) {
        this.cloudStorageBo = cloudStorageBo;
    }

    public DataStoreFactory create(String tokenStoragePath) throws IOException {
        DataStoreFactory dataStoreFactory;

        if (cloudStorageBo.isValidObjectUri(tokenStoragePath)) {
            CloudStorageLocation tokenCloudStorageLocation = cloudStorageBo.mapAsCloudStorageLocation(tokenStoragePath);
            dataStoreFactory = new CloudStorageDataStoreFactory(cloudStorageBo, tokenCloudStorageLocation);
        } else {
            dataStoreFactory = new FileDataStoreFactory(new File(tokenStoragePath));
        }

        return dataStoreFactory;
    }
}
