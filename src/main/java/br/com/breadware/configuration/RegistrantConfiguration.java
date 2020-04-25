package br.com.breadware.configuration;

import br.com.breadware.bo.CloudStorageBo;
import br.com.breadware.google.cloud.httprequestinitializer.datastore.CloudStorageDataStoreFactory;
import br.com.breadware.model.CloudStorageLocation;
import br.com.breadware.properties.RegistrantProperties;
import br.com.breadware.properties.google.GcpAuthorizationProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;

@Configuration
public class RegistrantConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrantConfiguration.class);

    @Bean(BeanNames.LOCALE)
    public Locale createLocale(RegistrantProperties registrantProperties) {
        return Locale.forLanguageTag(registrantProperties.getLocale());
    }

    @Bean(BeanNames.ZONE_ID)
    public ZoneId createZoneId(RegistrantProperties registrantProperties) {
        final ZoneId zoneId = ZoneId.of(registrantProperties.getZoneId());
        LOGGER.info("Operating system zone id is {}.", ZoneId.systemDefault());
        LOGGER.info("Program zone ID is {}.", zoneId);
        return zoneId;
    }

    @Bean(BeanNames.OBJECT_MAPPER)
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    @Bean(BeanNames.DATA_STORE_FACTORY)
    public DataStoreFactory createDataStoreFactory(CloudStorageBo cloudStorageBo, GcpAuthorizationProperties gcpAuthorizationProperties) throws IOException {
        DataStoreFactory dataStoreFactory;

        String tokensStorageLocation = gcpAuthorizationProperties.getTokensStoragePath();
        if (cloudStorageBo.isValidObjectUri(tokensStorageLocation)) {
            CloudStorageLocation clientIdCloudStorageLocation = cloudStorageBo.mapAsCloudStorageLocation(tokensStorageLocation);
            dataStoreFactory = new CloudStorageDataStoreFactory(cloudStorageBo, clientIdCloudStorageLocation);
        } else {
            dataStoreFactory = new FileDataStoreFactory(new File(tokensStorageLocation));
        }
        LOGGER.info("Created a \"{}\" object to attend \"{}\" inject beans.", dataStoreFactory.getClass(), DataStoreFactory.class);
        return dataStoreFactory;
    }
}
