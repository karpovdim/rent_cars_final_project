package by.karpov.rent_cars_final_project.dao;

import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.exception.DaoException;

import java.util.List;
import java.util.Optional;
/**
 * The Interface CarDao.
 */
public interface CarDao {

    /**
     * Find car by id.
     *
     * @param id the car id
     * @return the optional car
     * @throws DaoException the dao exception
     */
    Optional<Car> findById(Long id) throws DaoException;

/**
 * Find all cars
 * @return the list of cars
 * @throws DaoException
 *
 * */
    List<Car> findAll() throws DaoException;

    /**
     * Find car by limit.
     *
     * @param leftBorderCars    the left border
     * @param limitCarsOnPage the number of lines
     * @return the list of cars
     * @throws DaoException the dao exception
     */
    List<Car> findByLimit(int leftBorderCars, int limitCarsOnPage) throws DaoException;

    /**
     * Update car
     *
     * @param car
     * @return update car
     * @throws DaoException
     */
    Car update(Car car) throws DaoException;

    /**
     * create new car
     *
     * @param car
     * @return new car
     * @throws DaoException
     */
    Car create(Car car) throws DaoException;

    /**
     *
     * delete car
     * @param car
     * @throws DaoException
     */
    void delete(Car car) throws DaoException;

    /**
     * Find car by manufacture.
     *
     * @param regNumber the car manufacturer
     * @return the list of cars
     * @throws DaoException the dao exception
     */
    Optional<Car> findByRegistrationNumber(String regNumber) throws DaoException;
}
