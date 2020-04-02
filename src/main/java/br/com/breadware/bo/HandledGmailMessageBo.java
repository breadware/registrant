package br.com.breadware.bo;

import br.com.breadware.dao.HandledGmailMessageDao;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.model.HandledGmailMessage;
import br.com.breadware.model.mapper.ObjectToDataMapMapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HandledGmailMessageBo {

    private final HandledGmailMessageDao handledGmailMessageDao;

    private final ObjectToDataMapMapper objectToDataMapMapper;

    @Inject
    public HandledGmailMessageBo(HandledGmailMessageDao handledGmailMessageDao, ObjectToDataMapMapper objectToDataMapMapper) {
        this.handledGmailMessageDao = handledGmailMessageDao;
        this.objectToDataMapMapper = objectToDataMapMapper;
    }

    public void set(HandledGmailMessage handledGmailMessage) throws DataAccessException {
        Map<String, Object> dataMap = objectToDataMapMapper.mapTo(handledGmailMessage);
        handledGmailMessageDao.set(handledGmailMessage.getId(), dataMap);
    }

    public Set<HandledGmailMessage> getAll() throws DataAccessException {
        return handledGmailMessageDao.getAll()
                .stream()
                .map(dataMap -> objectToDataMapMapper.mapFrom(dataMap, HandledGmailMessage.class))
                .collect(Collectors.toSet());
    }
}
