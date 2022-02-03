package by.karpov.rent_cars_final_project.service.impl;

import by.karpov.rent_cars_final_project.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface Service<K> {
    Optional<K> findById(long id) throws ServiceException;

    List<K> findAll() throws ServiceException;

    K create(K k) throws ServiceException;

    K update(K k) throws ServiceException;

    void delete(K k) throws ServiceException;
}
