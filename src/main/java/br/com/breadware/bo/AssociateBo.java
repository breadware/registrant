package br.com.breadware.bo;

import br.com.breadware.dao.AssociateDao;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.Associate;
import br.com.breadware.model.mapper.AssociateToObjectListTwoWayMapper;
import br.com.breadware.model.message.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AssociateBo {

    private final AssociateDao associateDao;

    private final AssociateToObjectListTwoWayMapper associateToObjectListTwoWayMapper;

    private final ObjectMapper objectMapper;

    @Inject
    public AssociateBo(AssociateDao associateDao, AssociateToObjectListTwoWayMapper associateToObjectListTwoWayMapper, ObjectMapper objectMapper) {
        this.associateDao = associateDao;
        this.associateToObjectListTwoWayMapper = associateToObjectListTwoWayMapper;
        this.objectMapper = objectMapper;
    }

    public List<Associate> getAll() throws DataAccessException {
        ValueRange valueRange = associateDao.getAll();

        return Optional.ofNullable(valueRange.getValues())
                .orElseGet(ArrayList::new)
                .stream()
                .map(associateToObjectListTwoWayMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public Associate put(final Associate associate) throws DataAccessException {

        List<Associate> associates = getAll();

        Optional<Associate> optionalExistingAssociate = associates.stream()
                .filter(existingAssociate -> existingAssociate.equals(associate))
                .findFirst();

        Associate associateToPersist;
        if (optionalExistingAssociate.isPresent()) {
            Associate existingAssociate = optionalExistingAssociate.get();
            associates.remove(associate);
            associateToPersist = merge(existingAssociate, associate);
        } else {
            associateToPersist = associate;
        }

        associates.add(associateToPersist);
        associates = sortByCpf(associates);


        List<List<Object>> dataList = associates.stream()
                .map(associateToObjectListTwoWayMapper::mapTo)
                .collect(Collectors.toList());

        ValueRange valueRange = new ValueRange().setValues(dataList);
        associateDao.replace(valueRange);

        return associateToPersist;
    }

    private Associate merge(Associate first, Associate second) {

        Associate mergedAssociate = clone(first);

        BeanWrapper mergedAssociateBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(mergedAssociate);
        PropertyDescriptor[] mergedAssociatePropertyDescriptors = mergedAssociateBeanWrapper.getPropertyDescriptors();
        BeanWrapper secondBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(second);

        for (PropertyDescriptor mergedAssociatePropertyDescriptor : mergedAssociatePropertyDescriptors) {
            String propertyName = mergedAssociatePropertyDescriptor.getName();
            Object value = secondBeanWrapper.getPropertyValue(propertyName);
            if (mergedAssociateBeanWrapper.isWritableProperty(propertyName)) {
                mergedAssociateBeanWrapper.setPropertyValue(propertyName, value);
            }
        }

        Object wrappedInstance = mergedAssociateBeanWrapper.getWrappedInstance();

        if (!(wrappedInstance instanceof Associate)) {
            throw new RegistrantRuntimeException(ErrorMessage.WRAPPED_OBJECT_IS_NOT_OF_EXPECTED_TYPE, Associate.class);
        }

        return (Associate) wrappedInstance;
    }

    private Associate clone(Associate associate) {
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(associate), Associate.class);
        } catch (JsonProcessingException exception) {
            throw new RegistrantRuntimeException(exception, ErrorMessage.ERROR_CLONING_OBJECT, Associate.class);
        }
    }

    private List<Associate> sortByCpf(List<Associate> associates) {
        return associates.stream()
                .sorted(Comparator.comparing(Associate::getCpf))
                .collect(Collectors.toList());
    }
}
