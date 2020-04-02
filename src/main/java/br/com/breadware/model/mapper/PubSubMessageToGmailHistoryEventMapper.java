package br.com.breadware.model.mapper;

import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.GmailHistoryEvent;
import br.com.breadware.model.message.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PubSubMessageToGmailHistoryEventMapper implements Mapper<PubsubMessage, GmailHistoryEvent> {

    private final ObjectMapper objectMapper;

    public PubSubMessageToGmailHistoryEventMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public GmailHistoryEvent map(PubsubMessage pubsubMessage) {
        String messageContent = pubsubMessage.getData().toStringUtf8();
        try {
            return objectMapper.readValue(messageContent, GmailHistoryEvent.class);
        } catch (IOException exception) {
            throw new RegistrantRuntimeException(exception, ErrorMessage.ERROR_WHILE_MAPPING, PubsubMessage.class, GmailHistoryEvent.class);
        }
    }
}
