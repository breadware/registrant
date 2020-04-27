package br.com.breadware.bo;

import br.com.breadware.dao.CloudStorageDao;
import br.com.breadware.model.CloudStorageLocation;
import br.com.breadware.model.mapper.StringToCloudStorageMapper;
import com.google.cloud.storage.Blob;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.Serializable;
import java.nio.channels.Channels;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class CloudStorageBo {

    public static final String BUCKET_REGEX_GROUP_NAME = "bucket";

    public static final String OBJECT_REGEX_GROUP_NAME = "object";

    public static final String CLOUD_STORAGE_URI_REGEX = "gs://(?<" + BUCKET_REGEX_GROUP_NAME + ">[^/]+)/(?<" + OBJECT_REGEX_GROUP_NAME + ">.*)";

    public static final Pattern CLOUD_STORAGE_URI_PATTERN = Pattern.compile(CLOUD_STORAGE_URI_REGEX);

    private final StringToCloudStorageMapper stringToCloudStorageMapper;

    private final CloudStorageDao cloudStorageDao;

    public CloudStorageBo(StringToCloudStorageMapper stringToCloudStorageMapper, CloudStorageDao cloudStorageDao) {
        this.stringToCloudStorageMapper = stringToCloudStorageMapper;
        this.cloudStorageDao = cloudStorageDao;
    }

    public boolean isValidObjectUri(String uri) {
        return CLOUD_STORAGE_URI_PATTERN.matcher(uri)
                .matches();
    }

    public CloudStorageLocation mapAsCloudStorageLocation(String uri) {
        return stringToCloudStorageMapper.map(uri);
    }

    public Optional<Blob> retrieveAsBlob(String objectUri) {
        return retrieveAsBlob(mapAsCloudStorageLocation(objectUri));
    }

    public Optional<Blob> retrieveAsBlob(CloudStorageLocation cloudStorageLocation) {
        Blob blob = cloudStorageDao.get(cloudStorageLocation);
        if (!Objects.isNull(blob) && blob.exists()) {
            return Optional.of(blob);
        }
        return Optional.empty();
    }

    public Optional<InputStream> retrieveAsInputStream(String objectUri) {
        return retrieveAsInputStream(mapAsCloudStorageLocation(objectUri));
    }

    public Optional<InputStream> retrieveAsInputStream(CloudStorageLocation cloudStorageLocation) {
        Optional<Blob> optionalBlob = retrieveAsBlob(cloudStorageLocation);
        if (optionalBlob.isPresent()) {
            Blob blob = optionalBlob.get();
            InputStream inputStream = Channels.newInputStream(blob.reader());
            return Optional.of(inputStream);
        }
        return Optional.empty();
    }

    public void saveObject(CloudStorageLocation cloudStorageLocation, Serializable object) {
        Optional<Blob> optionalBlob = retrieveAsBlob(cloudStorageLocation);
        if (optionalBlob.isEmpty()) {
            create(cloudStorageLocation, object);
        } else {
            update(cloudStorageLocation, object);
        }
    }

    public void create(CloudStorageLocation cloudStorageLocation, Serializable object) {
        byte[] data = SerializationUtils.serialize(object);
        cloudStorageDao.create(cloudStorageLocation, data);
    }

    public void update(CloudStorageLocation cloudStorageLocation, Serializable object) {
        byte[] data = SerializationUtils.serialize(object);
        cloudStorageDao.update(cloudStorageLocation, data);

    }
}
