package org.example.repository.dbconstants;

public enum UsertableConstants {
    ID("id"),
    LOGIN("login"),
    PASSWORD("password");

    private String fieldName;

    private UsertableConstants(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
