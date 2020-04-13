package br.com.breadware.model.mapper;

import br.com.breadware.model.Associate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class AssociateToObjectListTwoWayMapper implements TwoWayMapper<Associate, List<Object>> {

    @Override
    public List<Object> mapTo(Associate associate) {

        if (Objects.isNull(associate)) {
            return Collections.emptyList();
        }

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(associate);

        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        List<Object> values = new ArrayList<>(propertyDescriptors.length);

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            values.add(propertyDescriptor.getValue(propertyDescriptor.getName())
                    .toString());
        }

        return values;
    }

    @Override
    public Associate mapFrom(List<Object> objects) {

        if (Objects.isNull(objects)) {
            return null;
        }

        if (CollectionUtils.isEmpty(objects)) {
            return new Associate();
        }

        long cpf = Long.parseLong(objects.get(FieldOrder.CPF.ordinal())
                .toString());

        String firstName = objects.get(FieldOrder.FIRST_NAME.ordinal())
                .toString();

        String lastName = objects.get(FieldOrder.LAST_NAME.ordinal())
                .toString();

        String address = objects.get(FieldOrder.ADDRESS.ordinal())
                .toString();

        String phone = objects.get(FieldOrder.PHONE.ordinal())
                .toString();

        return Associate.Builder.anAssociate()
                .cpf(cpf)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .phone(phone)
                .build();

    }

    private enum FieldOrder {
        CPF,
        FIRST_NAME,
        LAST_NAME,
        ADDRESS,
        PHONE
    }
}
