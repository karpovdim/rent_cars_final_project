package by.karpov.rent_cars_final_project.dao;

import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    Optional<Car> findById(Long id) throws DaoException;

    List<Car> findAll() throws DaoException;

    List<Car> findByLimit(int leftBorderCars, int limitCarsOnPage) throws DaoException;

    Car update(Car car) throws DaoException;

    Car create(Car car) throws DaoException;

    void delete(Car car) throws DaoException;

    Optional<Car> findByRegistrationNumber(String regNumber) throws DaoException;
}
