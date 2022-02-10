package by.karpov.rent_cars_final_project.service;

import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CarService {
    Optional<Car> findById(long id) throws ServiceException;

    List<Car> findAll() throws ServiceException;

    Car create(Car car) throws ServiceException;

    Car update(Car car) throws ServiceException;

    void delete(Car car) throws ServiceException;

    double countCars() throws ServiceException;

    List<Car> findByLimit(int leftBorderCars, int limitCarsOnPage) throws ServiceException;

    Optional<Car> updateStatus(long carId, Car.CarStatus carStatus) throws ServiceException;

    Optional<Car> updateCost(long carId, BigDecimal cost) throws ServiceException;

    boolean addCar(Map<String, String> parameters)throws ServiceException;

    Optional<Car> findByRegistrationNumber(String regNumber) throws ServiceException;

    List<Car> findByManufacture(String manufacturer) throws ServiceException;

}