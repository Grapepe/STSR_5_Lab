package org.example.builder;

import org.example.exceptions.RepositoryException;

import java.sql.ResultSet;

public interface Builder<T> {
    T build(ResultSet resultSet) throws RepositoryException;
}
