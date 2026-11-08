package org.example.builder;

import org.example.Model.Discipline;
import org.example.exceptions.RepositoryException;
import org.example.repository.dbconstants.DisciplinetableConstant;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisciplineBuilderFactory implements Builder<Discipline> {
    @Override
    public Discipline build(ResultSet resultSet) throws RepositoryException {
        try {
            int id = resultSet.getInt(DisciplinetableConstant.ID.getFieldName());
            String name = resultSet.getString(DisciplinetableConstant.NAME.getFieldName());
            String teacher = resultSet.getString(DisciplinetableConstant.TEACHER.getFieldName());
            return new Discipline(id, name, teacher);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}