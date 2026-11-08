package org.example.repository;

import org.example.Model.Discipline;
import org.example.builder.DisciplineBuilderFactory;
import org.example.exceptions.RepositoryException;
import org.example.repository.dbconstants.DisciplinetableConstant;
import org.example.repository.specification.Parameter;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DisciplineRepository extends AbstractRepository<Discipline> {
    private static final String TABLE_NAME = "disciplines";

    public DisciplineRepository(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public List<Discipline> query(String sqlString, Parameter parameter) throws RepositoryException {
        return executeQuery(sqlString, new DisciplineBuilderFactory(), parameter.getParameters());
    }

    @Override
    public Optional<Discipline> queryForSingleResult(String sqlString, Parameter parameter) throws RepositoryException {
        List<Discipline> list = query(sqlString, parameter);
        return list.size() == 1 ? Optional.of(list.get(0)) : Optional.empty();
    }

    @Override
    public Map<String, Object> getFields(Discipline discipline) {
        Map<String, Object> fields = new HashMap<>();
        fields.put(DisciplinetableConstant.NAME.getFieldName(), discipline.getName());
        fields.put(DisciplinetableConstant.TEACHER.getFieldName(), discipline.getTeacher());
        return fields;
    }
}