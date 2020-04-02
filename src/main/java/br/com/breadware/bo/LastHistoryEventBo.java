package br.com.breadware.bo;

import br.com.breadware.dao.LastHistoryEventDao;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.model.LastHistoryEvent;
import br.com.breadware.model.mapper.ObjectToDataMapMapper;
import br.com.breadware.properties.GcpAuthorizationProperties;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Map;

@Component
public class LastHistoryEventBo {

    private final LastHistoryEventDao lastHistoryEventDao;

    private final ObjectToDataMapMapper objectToDataMapMapper;

    private final GcpAuthorizationProperties gcpAuthorizationProperties;

    @Inject
    public LastHistoryEventBo(LastHistoryEventDao lastHistoryEventDao, ObjectToDataMapMapper objectToDataMapMapper, GcpAuthorizationProperties gcpAuthorizationProperties) {
        this.lastHistoryEventDao = lastHistoryEventDao;
        this.objectToDataMapMapper = objectToDataMapMapper;
        this.gcpAuthorizationProperties = gcpAuthorizationProperties;
    }

    public void set(LastHistoryEvent lastHistoryEvent) throws DataAccessException {
        Map<String, Object> dataMap = objectToDataMapMapper.mapTo(lastHistoryEvent);
        lastHistoryEventDao.set(gcpAuthorizationProperties.getAuthorizedUser(), dataMap);
    }

    public LastHistoryEvent get() throws DataAccessException {
        Map<String, Object> dataMap = lastHistoryEventDao.get(gcpAuthorizationProperties.getAuthorizedUser());
        return objectToDataMapMapper.mapFrom(dataMap, LastHistoryEvent.class);
    }
}
