package br.com.breadware.model.mapper;

import br.com.breadware.exception.MappingException;
import br.com.breadware.model.CloudStorageLocation;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.bo.CloudStorageBo;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Component
public class StringToCloudStorageMapper implements Mapper<String, CloudStorageLocation> {

    @Override
    public CloudStorageLocation map(String text) {
        Matcher matcher = CloudStorageBo.CLOUD_STORAGE_URI_PATTERN.matcher(text);
        if (!matcher.matches()) {
            throw new MappingException(ErrorMessage.TEXT_IS_NOT_CLOUD_STORAGE_URI, text);
        }

        String bucket = matcher.group(CloudStorageBo.BUCKET_REGEX_GROUP_NAME);
        String object = matcher.group(CloudStorageBo.OBJECT_REGEX_GROUP_NAME);

        return CloudStorageLocation.Builder.aCloudStorageLocation()
                .bucket(bucket)
                .object(object)
                .build();
    }
}
