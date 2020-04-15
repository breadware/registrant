package br.com.breadware.bo;

import br.com.breadware.dao.LastHistoryEventDao;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.exception.GmailHistoryRetrievalException;
import br.com.breadware.google.mail.GmailHistoryRetriever;
import br.com.breadware.model.GmailHistoryEvent;
import br.com.breadware.model.mapper.ObjectToDataMapMapper;
import br.com.breadware.model.mapper.PubSubMessageToGmailHistoryEventMapper;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.properties.google.GcpAuthorizationProperties;
import br.com.breadware.util.LoggerUtil;
import com.google.api.services.gmail.model.History;
import com.google.api.services.gmail.model.HistoryMessageAdded;
import com.google.api.services.gmail.model.ListHistoryResponse;
import com.google.api.services.gmail.model.Message;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GmailHistoryBo {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailHistoryBo.class);

    private final LastHistoryEventDao lastHistoryEventDao;

    private final ObjectToDataMapMapper objectToDataMapMapper;

    private final GcpAuthorizationProperties gcpAuthorizationProperties;

    private final LoggerUtil loggerUtil;

    private final PubSubMessageToGmailHistoryEventMapper pubSubMessageToGmailHistoryEventMapper;

    private final GmailHistoryRetriever gmailHistoryRetriever;

    @Inject
    public GmailHistoryBo(LastHistoryEventDao lastHistoryEventDao, ObjectToDataMapMapper objectToDataMapMapper, GcpAuthorizationProperties gcpAuthorizationProperties, LoggerUtil loggerUtil, PubSubMessageToGmailHistoryEventMapper pubSubMessageToGmailHistoryEventMapper, GmailHistoryRetriever gmailHistoryRetriever) {
        this.lastHistoryEventDao = lastHistoryEventDao;
        this.objectToDataMapMapper = objectToDataMapMapper;
        this.gcpAuthorizationProperties = gcpAuthorizationProperties;
        this.loggerUtil = loggerUtil;
        this.pubSubMessageToGmailHistoryEventMapper = pubSubMessageToGmailHistoryEventMapper;
        this.gmailHistoryRetriever = gmailHistoryRetriever;
    }

    public GmailHistoryEvent getLastHandledEvent() throws DataAccessException {
        Map<String, Object> dataMap = lastHistoryEventDao.get(gcpAuthorizationProperties.getAuthorizedUser());
        return objectToDataMapMapper.mapFrom(dataMap, GmailHistoryEvent.class);
    }

    public void setLastHandledEvent(GmailHistoryEvent gmailHistoryEvent) throws DataAccessException {
        Map<String, Object> dataMap = objectToDataMapMapper.mapTo(gmailHistoryEvent);
        lastHistoryEventDao.set(gcpAuthorizationProperties.getAuthorizedUser(), dataMap);
    }

    public GmailHistoryEvent getLastHandledEvent(GmailHistoryEvent gmailHistoryEvent) throws DataAccessException {

        GmailHistoryEvent lastHistoryEvent = getLastHandledEvent();

        if (!Objects.isNull(gmailHistoryEvent) && gmailHistoryEvent.getId()
                .compareTo(lastHistoryEvent.getId()) < 0) {
            loggerUtil.warn(LOGGER, LoggerMessage.EVENT_ID_RECEIVED_IS_PREVIOUS_TO_THE_LAST_ID, gmailHistoryEvent.getId(), lastHistoryEvent.getId());
            setLastHandledEvent(gmailHistoryEvent);
            lastHistoryEvent = gmailHistoryEvent;
        }

        return lastHistoryEvent;
    }

    public GmailHistoryEvent map(PubsubMessage pubsubMessage) {
        return pubSubMessageToGmailHistoryEventMapper.map(pubsubMessage);
    }

    public List<History> retrieveHistories(GmailHistoryEvent gmailHistoryEvent) throws GmailHistoryRetrievalException, DataAccessException {

        GmailHistoryEvent lastHandledEvent = getLastHandledEvent(gmailHistoryEvent);

        ListHistoryResponse listHistoryResponse = gmailHistoryRetriever.retrieve(lastHandledEvent);

        List<History> histories = listHistoryResponse.getHistory();

        if (CollectionUtils.isEmpty(histories)) {
            loggerUtil.info(LOGGER, LoggerMessage.GMAIL_HISTORY_RETRIEVAL_RETURNED_AN_EMPTY_LIST);
            return Collections.emptyList();
        }

        return histories;
    }

    public Set<Message> retrieveMessages(List<History> histories) {
        return histories.stream()
                .map(History::getMessagesAdded)
                .flatMap(List::stream)
                .map(HistoryMessageAdded::getMessage)
                .collect(Collectors.toSet());
    }
}
