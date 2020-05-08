package br.com.breadware.model.mapper;

import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;

@Component
public class ObjectToDataMapMapper {

    private final ObjectMapper objectMapper;

    @Inject
    public ObjectToDataMapMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> mapTo(Object input) {

        if (Objects.isNull(input)) {
            return null;
        }

        try {
            String json = objectMapper.writeValueAsString(input);
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException exception) {
            throw new RegistrantRuntimeException(exception, ErrorMessage.ERROR_WHILE_MAPPING, input.getClass(), Map.class);
        }

    }

    public <O> O mapFrom(Map<String, Object> map, Class<O> outputClass) {

        if (Objects.isNull(map)) {
            return null;
        }

        try {
            String json = objectMapper.writeValueAsString(map);
            return objectMapper.readValue(json, outputClass);
        } catch (Exception exception) {
            throw new RegistrantRuntimeException(exception, ErrorMessage.ERROR_WHILE_MAPPING, Map.class, outputClass);
        }
    }
}
