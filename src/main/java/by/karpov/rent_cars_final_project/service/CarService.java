package by.karpov.rent_cars_final_project.service;

import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *  CarService interface
 */
public interface CarService {
    /**
     * find car by id
     * @param id
     * @return optional of car
     * @throws ServiceException
     */
    Optional<Car> findById(long id) throws ServiceException;

    /**
     * find all cars
     * @return list of cars
     * @throws ServiceException
     */
    List<Car> findAll() throws ServiceException;

    /**
     * create new car
     *
     * @param car
     * @return new car
     * @throws ServiceException
     */
    Car create(Car car) throws ServiceException;

    /**
     * update car
     *
     * @param car
     * @return update car
     * @throws ServiceException
     */
    Car update(Car car) throws ServiceException;

    /**
     * delete car
     * @param car
     * @throws ServiceException
     */
    void delete(Car car) throws ServiceException;

    /**
     * count cars
     * @return double of all cars
     * @throws ServiceException
     */
    double countCars() throws ServiceException;

    /**
     * * find cars by limit
     * @param leftBorderCars
     * @param limitCarsOnPage
     * @return
     * @throws ServiceException
     */
    List<Car> findByLimit(int leftBorderCars, int limitCarsOnPage) throws ServiceException;

    /**
     * update status car
     * @param carId
     * @param carStatus
     * @return optional of car
     * @throws ServiceException
     */
    Optional<Car> updateStatus(long carId, Car.CarStatus carStatus) throws ServiceException;

    /**
     *  update coast
     * @param carId
     * @param cost
     * @return optional of cars
     * @throws ServiceException
     */
    Optional<Car> updateCost(long carId, BigDecimal cost) throws ServiceException;

    /**
     * add new car
     * @param parameters
     * @return return boolean result
     * @throws ServiceException
     */
    boolean addCar(Map<String, String> parameters)throws ServiceException;

    /**
     *  find car by registration number
     * @param regNumber
     * @return optional of car
     * @throws ServiceException
     */
    Optional<Car> findByRegistrationNumber(String regNumber) throws ServiceException;

    /**
     * find cars by manufacture
     * @param manufacturer
     * @return list of cars
     * @throws ServiceException
     */
    List<Car> findByManufacture(String manufacturer) throws ServiceException;

}