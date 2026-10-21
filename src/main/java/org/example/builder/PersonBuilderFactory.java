package org.example.builder;

import org.example.Model.Person;
import org.example.exceptions.RepositoryException;
import org.example.repository.dbconstants.PersontableConstant;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonBuilderFactory implements  Builder<Person>
{

    @Override
    public Person build(ResultSet resultSet) throws RepositoryException {
        try
        {
            int id = resultSet.getInt(PersontableConstant.ID.getFieldName());
            String name = resultSet.getString(PersontableConstant.NAME.getFieldName());
            String phone = resultSet.getString(PersontableConstant.PHONE.getFieldName());
            String email = resultSet.getString(PersontableConstant.EMAIL.getFieldName());
            return new Person(id, name, phone, email);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }
}
