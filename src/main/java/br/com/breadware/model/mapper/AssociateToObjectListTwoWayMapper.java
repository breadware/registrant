package br.com.breadware.model.mapper;

import br.com.breadware.exception.MappingException;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.Associate;
import br.com.breadware.model.AssociateFieldOrder;
import br.com.breadware.model.annotation.SkipOnMapping;
import br.com.breadware.model.message.ErrorMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;

@Component
public class AssociateToObjectListTwoWayMapper implements TwoWayMapper<Associate, List<Object>> {

    private static final String PROPERTY_DESCRIPTOR_CLASS_NAME = "class";

    @Override
    public List<Object> mapTo(Associate associate) {

        if (Objects.isNull(associate)) {
            return Collections.emptyList();
        }

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(associate);

        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Object[] values = new Object[AssociateFieldOrder.values().length];

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

            if (shouldSkipProperty(propertyDescriptor)) {
                continue;
            }

            Object propertyValue = beanWrapper.getPropertyValue(propertyDescriptor.getName());
            String propertyValueText = Optional.ofNullable(propertyValue)
                    .map(Object::toString)
                    .orElse(StringUtils.EMPTY);

            int index = AssociateFieldOrder.findByFieldName(propertyDescriptor.getName())
                    .ordinal();

            values[index] = propertyValueText;
        }

        return Arrays.asList(values);
    }

    private boolean shouldSkipProperty(PropertyDescriptor propertyDescriptor) {
        if (PROPERTY_DESCRIPTOR_CLASS_NAME.equals(propertyDescriptor.getName())) {
            return true;
        }

        try {
            Field field = Associate.class.getDeclaredField(propertyDescriptor.getName());
            return field.isAnnotationPresent(SkipOnMapping.class);
        } catch (NoSuchFieldException exception) {
            throw new MappingException(exception, ErrorMessage.ERROR_WHILE_MAPPING, Associate.class, List.class);
        }
    }

    @Override
    public Associate mapFrom(List<Object> objects) {

        if (Objects.isNull(objects)) {
            return null;
        }

        if (CollectionUtils.isEmpty(objects)) {
            return new Associate();
        }

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(new Associate());

        for (AssociateFieldOrder associateFieldOrder : AssociateFieldOrder.values()) {
            String propertyName = associateFieldOrder.getFieldName();
            String propertyValue = objects.get(AssociateFieldOrder.findByFieldName(propertyName).ordinal())
                    .toString();
            beanWrapper.setPropertyValue(propertyName, propertyValue);
        }

        Object wrappedInstance = beanWrapper.getWrappedInstance();

        if (!(wrappedInstance instanceof Associate)) {
            throw new RegistrantRuntimeException(ErrorMessage.WRAPPED_OBJECT_IS_NOT_OF_EXPECTED_TYPE, Associate.class);
        }

        return (Associate) wrappedInstance;
    }
}
