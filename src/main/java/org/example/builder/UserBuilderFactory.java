package org.example.builder;
import org.example.Model.User;
import org.example.exceptions.RepositoryException;
import org.example.repository.dbconstants.UsertableConstants;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBuilderFactory implements Builder<User>
{

    @Override
    public User build(ResultSet resultSet) throws RepositoryException {
        try
        {
            int id = resultSet.getInt(UsertableConstants.ID.getFieldName());
            String login = resultSet.getString(UsertableConstants.LOGIN.getFieldName());
            byte[] password = resultSet.getBytes(UsertableConstants.PASSWORD.getFieldName());
            return new User(login, password);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }
}
