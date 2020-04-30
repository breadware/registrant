package br.com.breadware.google.cloud.authorization.datastore;

import br.com.breadware.bo.CloudStorageBo;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.CloudStorageLocation;
import br.com.breadware.model.message.ErrorMessage;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Lists;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

public class CloudStorageDataStoreFactory implements DataStoreFactory {

    private final CloudStorageBo cloudStorageBo;
    private final CloudStorageLocation cloudStorageLocation;

    public CloudStorageDataStoreFactory(CloudStorageBo cloudStorageBo, CloudStorageLocation cloudStorageLocation) {
        this.cloudStorageBo = cloudStorageBo;
        this.cloudStorageLocation = cloudStorageLocation;
    }

    @Override
    public <V extends Serializable> DataStore<V> getDataStore(String id) {
        return new CloudStorageDataStore<>(this, id, cloudStorageBo, cloudStorageLocation);
    }

    static class CloudStorageDataStore<V extends Serializable> extends AbstractDataStore<V> {

        private final HashMap<String, byte[]> dataMap;
        private final CloudStorageBo cloudStorageBo;
        private final CloudStorageLocation cloudStorageLocation;

        public CloudStorageDataStore(DataStoreFactory dataStoreFactory, String id, CloudStorageBo cloudStorageBo, CloudStorageLocation cloudStorageLocation) {
            super(dataStoreFactory, id);
            this.cloudStorageBo = cloudStorageBo;
            this.cloudStorageLocation = cloudStorageLocation;
            this.dataMap = retrieveOrCreateDataMap();
        }

        private HashMap<String, byte[]> retrieveOrCreateDataMap() {
            Optional<InputStream> optionalDataMapInputStream = cloudStorageBo.retrieveAsInputStream(cloudStorageLocation);

            return optionalDataMapInputStream.map(this::readDataMapFromInputStream)
                    .orElseGet(HashMap::new);
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        private HashMap<String, byte[]> readDataMapFromInputStream(InputStream dataMapInputStream) {
            try {
                ObjectInputStream dataMapObjectInputStream = new ObjectInputStream(dataMapInputStream);
                Object object = dataMapObjectInputStream.readObject();

                if (!(object instanceof HashMap)) {
                    throw new RegistrantRuntimeException(ErrorMessage.OBJECT_ON_CLOUD_STORAGE_IS_NOT_HASHMAP);
                }
                return (HashMap) object;
            } catch (IOException | ClassNotFoundException exception) {
                throw new RegistrantRuntimeException(ErrorMessage.ERROR_RETRIEVING_DATA_MAP_FROM_CLOUD_STORAGE, exception);
            }
        }

        @Override
        public synchronized Set<String> keySet() {
            return Collections.unmodifiableSet(dataMap.keySet());
        }

        @Override
        public synchronized Collection<V> values() throws IOException {
            List<V> result = Lists.newArrayList();
            for (byte[] bytes : dataMap.values()) {
                result.add(IOUtils.deserialize(bytes));
            }
            return Collections.unmodifiableList(result);
        }

        @Override
        public synchronized V get(String key) throws IOException {

            if (Objects.isNull(key)) {
                return null;
            }

            return IOUtils.deserialize(dataMap.get(key));
        }

        @Override
        public synchronized DataStore<V> set(String key, V value) throws IOException {
            Assert.notNull(key, "Argument \"key\" cannot be null.");
            Assert.notNull(value, "Argument \"value\" cannot be null.");

            dataMap.put(key, IOUtils.serialize(value));
            save();

            return this;
        }

        @Override
        public synchronized DataStore<V> clear() {
            dataMap.clear();
            save();
            return null;
        }

        @Override
        public synchronized DataStore<V> delete(String key) {
            if (Objects.isNull(key)) {
                return this;
            }

            dataMap.remove(key);
            save();
            return this;
        }

        public void save() {
            cloudStorageBo.saveObject(cloudStorageLocation, dataMap);
        }
    }
}
