package org.example.repository.dbconstants;

public enum PersontableConstant
{
    ID("id"),
    NAME("name"),
    PHONE("phone"),
    EMAIL("email");

    private String fieldName;

    private PersontableConstant(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getFieldName()
    {
        return fieldName;
    }
}
