package br.com.breadware.model;

public enum AssociateFieldOrder {
    CPF("cpf"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    ADDRESS("address"),
    PHONE("phone"),
    EMAIL("email");

    private final String fieldName;

    AssociateFieldOrder(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static AssociateFieldOrder findByFieldName(String fieldName) {
        for (AssociateFieldOrder associateFieldOrder : AssociateFieldOrder.values()) {
            if (associateFieldOrder.fieldName.equals(fieldName)) {
                return associateFieldOrder;
            }
        }
        throw new IllegalArgumentException("Unknown field \"" + fieldName + "\".");
    }
}
