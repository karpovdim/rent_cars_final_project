package by.karpov.rent_cars_final_project.dao;

import by.karpov.rent_cars_final_project.entity.Insurance;
import by.karpov.rent_cars_final_project.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface InsuranceDao {
    Optional<Insurance> findById(Long id) throws DaoException;

    List<Insurance> findAll() throws DaoException;

    Insurance create(Insurance insurance) throws DaoException;

    Insurance update(Insurance insurance) throws DaoException;

    void delete(Insurance insurance) throws DaoException;
}
