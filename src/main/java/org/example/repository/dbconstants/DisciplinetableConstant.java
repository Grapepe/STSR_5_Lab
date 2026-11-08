package org.example.repository.dbconstants;

public enum DisciplinetableConstant {
    ID("id"),
    NAME("name"),
    TEACHER("teacher");

    private String fieldName;

    DisciplinetableConstant(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}