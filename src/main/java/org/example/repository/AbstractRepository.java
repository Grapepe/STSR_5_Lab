package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.builder.Builder;
import org.example.builder.BuilderFactory;
import org.example.exceptions.RepositoryException;
import org.example.repository.specification.Parameter;

import java.sql.*;
import java.util.*;

public abstract class AbstractRepository<T> implements Repository<T> {
    private static final Logger logger = LogManager.getLogger(AbstractRepository.class);
    private static final String GET_ALL_QUERY = "SELECT * FROM ";

    private final Connection connection;

    protected AbstractRepository(Connection connection) {
        this.connection = connection;
    }

    protected abstract String getTableName();

    protected abstract Map<String, Object> getFields(T obj);

    public static void prepare(PreparedStatement preparedStatement, List<Object> parameters)
            throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            preparedStatement.setObject(i + 1, parameters.get(i));
        }
    }

    public static void prepare(PreparedStatement preparedStatement, Map<String, Object> fields)
            throws SQLException {
        int i = 1;
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            if (SQLHelper.ID.equals(entry.getKey())) {
                continue;
            }
            preparedStatement.setObject(i++, entry.getValue());
        }
    }

    protected List<T> executeQuery(String sql,
                                   Builder<T> builder,
                                   List<Object> parameters) throws RepositoryException {
        List<T> objects = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            prepare(preparedStatement, parameters);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    objects.add(builder.build(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }

        return objects;
    }

    protected Optional<T> executeQueryForSingleResult(String sql,
                                                      Builder<T> builder,
                                                      List<Object> parameters)
            throws RepositoryException {
        List<T> items = executeQuery(sql, builder, parameters);
        return items.size() == 1 ? Optional.of(items.get(0)) : Optional.empty();
    }

    @Override
    public List<T> findAll() throws RepositoryException {
        Builder<T> builder = BuilderFactory.create(getTableName());
        String query = GET_ALL_QUERY + getTableName();
        return executeQuery(query, builder, Collections.emptyList());
    }

    @Override
    public Integer save(T object) throws RepositoryException {
        Map<String, Object> fields = getFields(object);
        String sql = SQLHelper.makeInsertQuery(fields, getTableName());
        return executeSave(sql, fields);
    }

    private Integer executeSave(String query, Map<String, Object> fields)
            throws RepositoryException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            prepare(preparedStatement, fields);
            logger.info(preparedStatement.toString());
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}