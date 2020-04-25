package br.com.breadware.google.cloud.storage;

import br.com.breadware.model.CloudStorageLocation;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageClass;
import org.springframework.stereotype.Component;

@Component
public class CloudStorageDao {

    private final Storage storage;

    public CloudStorageDao(Storage storage) {
        this.storage = storage;
    }

    public Blob get(CloudStorageLocation cloudStorageLocation) {
        return storage.get(cloudStorageLocation.getBucket(), cloudStorageLocation.getObject());
    }

    public void create(CloudStorageLocation cloudStorageLocation, byte[] bytes) {
        BlobInfo blobInfo = mapAsBlobInfo(cloudStorageLocation);
        storage.create(blobInfo, bytes);
    }

    public void delete(CloudStorageLocation cloudStorageLocation) {
        storage.delete(cloudStorageLocation.getBucket(), cloudStorageLocation.getObject());
    }

    public void update(CloudStorageLocation cloudStorageLocation, byte[] data) {
        delete(cloudStorageLocation);
        create(cloudStorageLocation, data);
    }

    private BlobInfo mapAsBlobInfo(CloudStorageLocation cloudStorageLocation) {
        return BlobInfo.newBuilder(cloudStorageLocation.getBucket(), cloudStorageLocation.getObject())
                .setStorageClass(StorageClass.STANDARD).build();
    }
}
