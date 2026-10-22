package org.example.repository;

import org.example.Model.Person;
import org.example.builder.PersonBuilderFactory;
import org.example.exceptions.RepositoryException;
import org.example.repository.dbconstants.PersontableConstant;
import org.example.repository.specification.Parameter;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PersonRepository extends AbstractRepository<Person> {
    private static final String TABLE_NAME = "persons";

    public PersonRepository(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public List<Person> query(String sqlString, Parameter parameter) throws RepositoryException {
        return executeQuery(sqlString, new PersonBuilderFactory(), parameter.getParameters());
    }

    @Override
    public Optional<Person> queryForSingleResult(String sqlString, Parameter parameter)
            throws RepositoryException {
        List<Person> persons = query(sqlString, parameter);
        return persons.size() == 1 ? Optional.of(persons.get(0)) : Optional.empty();
    }

    @Override
    public Map<String, Object> getFields(Person person) {
        Map<String, Object> fields = new HashMap<>();
        fields.put(PersontableConstant.NAME.getFieldName(), person.getName());
        fields.put(PersontableConstant.PHONE.getFieldName(), person.getPhone());
        fields.put(PersontableConstant.EMAIL.getFieldName(), person.getEmail());
        return fields;
    }
}