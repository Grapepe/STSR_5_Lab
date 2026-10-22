package org.example.repository;

import org.example.exceptions.RepositoryException;
import org.example.repository.specification.Parameter;

import java.awt.image.ReplicateScaleFilter;
import java.util.List;
import java.util.Optional;

public interface Repository<T>
{
    List<T> query(String sqlString, Parameter parameter) throws RepositoryException;
    Optional<T> queryForSingleResult(String sqlString, Parameter parameter) throws  RepositoryException;
    List<T> findAll() throws RepositoryException;
    Integer save(T object) throws RepositoryException;
}
