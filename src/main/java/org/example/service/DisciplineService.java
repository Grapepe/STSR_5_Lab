package org.example.service;

import org.example.Model.Discipline;
import org.example.exceptions.RepositoryException;
import org.example.exceptions.ServiceException;
import org.example.repository.DisciplineRepository;
import org.example.repository.RepositoryCreator;

import java.util.List;

public class DisciplineService {
    public List<Discipline> findAll() throws ServiceException {
        try (RepositoryCreator repositoryCreator = new RepositoryCreator()) {
            DisciplineRepository repository = repositoryCreator.getDisciplineRepository();
            return repository.findAll();
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void save(Discipline discipline) throws ServiceException {
        try (RepositoryCreator repositoryCreator = new RepositoryCreator()) {
            DisciplineRepository repository = repositoryCreator.getDisciplineRepository();
            repository.save(discipline);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}