package br.com.breadware.bo;

import br.com.breadware.dao.AssociateDao;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.model.Associate;
import br.com.breadware.model.mapper.AssociateToObjectListTwoWayMapper;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AssociateBo {

    private final AssociateDao associateDao;

    private final AssociateToObjectListTwoWayMapper associateToObjectListTwoWayMapper;

    @Inject
    public AssociateBo(AssociateDao associateDao, AssociateToObjectListTwoWayMapper associateToObjectListTwoWayMapper) {
        this.associateDao = associateDao;
        this.associateToObjectListTwoWayMapper = associateToObjectListTwoWayMapper;
    }

    public List<Associate> getAll() throws DataAccessException {
        ValueRange valueRange = associateDao.getAll();

        return Optional.ofNullable(valueRange.getValues())
                .orElseGet(ArrayList::new)
                .stream()
                .map(associateToObjectListTwoWayMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void put(Associate associate) throws DataAccessException {
        List<Object> associateDataList = associateToObjectListTwoWayMapper.mapTo(associate);
        List<List<Object>> dataList = Collections.singletonList(associateDataList);
        ValueRange valueRange = new ValueRange().setValues(dataList);
        associateDao.append(valueRange);
    }
}
