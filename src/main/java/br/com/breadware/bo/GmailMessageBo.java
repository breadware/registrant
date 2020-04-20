package br.com.breadware.bo;

import br.com.breadware.dao.HandledGmailMessageDao;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.exception.MessageHandlingException;
import br.com.breadware.exception.RegistrantException;
import br.com.breadware.google.mail.message.composer.GmailMessageComposer;
import br.com.breadware.google.mail.message.GmailMessageHandler;
import br.com.breadware.google.mail.message.GmailMessageRetriever;
import br.com.breadware.google.mail.message.GmailMessageSender;
import br.com.breadware.model.HandledGmailMessage;
import br.com.breadware.model.mapper.ObjectToDataMapMapper;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.util.LoggerUtil;
import com.google.api.services.gmail.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GmailMessageBo {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailMessageBo.class);

    private final HandledGmailMessageDao handledGmailMessageDao;

    private final ObjectToDataMapMapper objectToDataMapMapper;

    private final GmailMessageHandler gmailMessageHandler;

    private final GmailMessageRetriever gmailMessageRetriever;

    private final LoggerUtil loggerUtil;

    private final GmailMessageComposer gmailMessageComposer;

    private final GmailMessageSender gmailMessageSender;

    @Inject
    public GmailMessageBo(HandledGmailMessageDao handledGmailMessageDao, ObjectToDataMapMapper objectToDataMapMapper, GmailMessageHandler gmailMessageHandler, GmailMessageRetriever gmailMessageRetriever, LoggerUtil loggerUtil, GmailMessageComposer gmailMessageComposer, GmailMessageSender gmailMessageSender) {
        this.handledGmailMessageDao = handledGmailMessageDao;
        this.objectToDataMapMapper = objectToDataMapMapper;
        this.gmailMessageHandler = gmailMessageHandler;
        this.gmailMessageRetriever = gmailMessageRetriever;
        this.loggerUtil = loggerUtil;
        this.gmailMessageComposer = gmailMessageComposer;
        this.gmailMessageSender = gmailMessageSender;
    }

    public void addHandledGmailMessage(HandledGmailMessage handledGmailMessage) throws DataAccessException {
        Map<String, Object> dataMap = objectToDataMapMapper.mapTo(handledGmailMessage);
        handledGmailMessageDao.set(handledGmailMessage.getId(), dataMap);
    }

    public Set<Message> retrieveAlreadyHandledMessages(Set<Message> messages) throws DataAccessException {
        Set<String> handledGmailMessageIds = getHandledGmailMessageIds();

        return messages.stream()
                .filter(message -> handledGmailMessageIds.contains(message.getId()))
                .collect(Collectors.toSet());
    }

    private Set<String> getHandledGmailMessageIds() throws DataAccessException {
        return handledGmailMessageDao.getAll()
                .stream()
                .map(dataMap -> objectToDataMapMapper.mapFrom(dataMap, HandledGmailMessage.class))
                .map(HandledGmailMessage::getId)
                .collect(Collectors.toSet());
    }

    public void handle(Message message) {
        try {
            Message completeMessage = gmailMessageRetriever.retrieve(message.getId());
            gmailMessageHandler.handle(completeMessage);
            signalMessageAsHandled(completeMessage);
        } catch (RegistrantException exception) {
            throw new MessageHandlingException(exception, ErrorMessage.ERROR_WHILE_HANDLING_GMAIL_MESSAGE, message.getId());
        }
    }

    private void signalMessageAsHandled(Message message) throws DataAccessException {
        long time = LocalDateTime.now()
                .toEpochSecond(ZoneOffset.UTC);

        HandledGmailMessage handledGmailMessage = HandledGmailMessage.Builder.aHandledGmailMessage()
                .id(message.getId())
                .time(time)
                .build();

        addHandledGmailMessage(handledGmailMessage);
        loggerUtil.info(LOGGER, LoggerMessage.GMAIL_MESSAGE_HAS_BEEN_HANDLED, message.getId());
    }
}
