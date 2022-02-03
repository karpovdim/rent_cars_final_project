package by.karpov.rent_cars_final_project.service.impl;

import by.karpov.rent_cars_final_project.dao.CarDao;
import by.karpov.rent_cars_final_project.dao.impl.CarDaoImpl;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.exception.DaoException;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.CarService;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.karpov.rent_cars_final_project.command.RequestParameter.*;

public class CarServiceImpl implements CarService {
    private static final Logger LOGGER = LogManager.getLogger(CarServiceImpl.class);
    private static final CarServiceImpl instance = new CarServiceImpl();
    private final CarDao carDaoImpl = CarDaoImpl.getInstance();
    private final InputDataValidator validator = InputDataValidator.getInstance();

    private CarServiceImpl() {
    }

    public static CarServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Car> findById(long id) throws ServiceException {
        LOGGER.info("method findById() Car");
        final Optional<Car> optionalCar;
        try {
            optionalCar = carDaoImpl.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Error while find by id task [{}]", id);
            throw new ServiceException(e);
        }
        return optionalCar;
    }

    @Override
    public List<Car> findAll() throws ServiceException {
        LOGGER.info("method findAll() Car");
        final List<Car> users;
        try {
            users = carDaoImpl.findAll();
        } catch (DaoException e) {
            LOGGER.error("Error while findAll task ");
            throw new ServiceException(e);
        }
        return List.copyOf(users);
    }

    @Override
    public Car create(Car car) throws ServiceException {
        LOGGER.info("method create() Car");
        Car createCar = null;
        try {
            createCar = carDaoImpl.create(car);
        } catch (DaoException e) {
            LOGGER.error("Error while creating task [{}]", car);
            throw new ServiceException(e);
        }
        return createCar;
    }

    @Override
    public Car update(Car car) throws ServiceException {
        LOGGER.info("method update() Car");
        Car updateCar;
        try {
            updateCar = carDaoImpl.update(car);
        } catch (DaoException e) {
            LOGGER.error("Error while updating task [{}]", car);
            throw new ServiceException(e);
        }
        return updateCar;
    }

    @Override
    public void delete(Car car) throws ServiceException {
        LOGGER.info("method delete() Car");
        try {
            carDaoImpl.delete(car);
        } catch (DaoException e) {
            LOGGER.error("Error while deleting task [{}]", car);
            throw new ServiceException(e);
        }
    }

    @Override
    public double countCars() throws ServiceException {
        LOGGER.info("method countCars()");
        final List<Car> cars;
        try {
            cars = carDaoImpl.findAll();
        } catch (DaoException e) {
            LOGGER.error("exception in method countCars()", e);
            throw new ServiceException("Exception in method countCars()", e);
        }
        return cars.size();
    }

    @Override
    public List<Car> findByLimit(int leftBorderCars, int limitCarsOnPage) throws ServiceException {
        LOGGER.info("method findByLimit() Car");
        try {
            return carDaoImpl.findByLimit(leftBorderCars, limitCarsOnPage);
        } catch (DaoException e) {
            LOGGER.error("exception in method findByLimit()", e);
            throw new ServiceException("Exception when find user by limit", e);
        }
    }
    @Override
    public Optional<Car> updateStatus(long carId, Car.CarStatus carStatus) throws ServiceException {
        LOGGER.info("method updateStatus() Car");

        if (validator.isIdValid(carId) && findById(carId).isPresent()) {
            final var car = findById(carId).get();
            car.setCarStatus(carStatus);
            final var updateCar = update(car);
            return Optional.of(updateCar);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Car> updateCost(long carId, BigDecimal cost ) throws ServiceException {
        LOGGER.info("method updateCost() Car");
        if (validator.isIdValid(carId) && findById(carId).isPresent()) {
            final var car = findById(carId).get();
            car.setCost(cost);
            final var updateCar = update(car);
            return Optional.of(updateCar);
        }
        return Optional.empty();
    }

    @Override
    public void addCar(Map<String, String> parameters) throws ServiceException {
        LOGGER.info("method add() Car");
        final var car = Car.builder()
                .descriptionCar(parameters.get(CAR_DESCRIPTION))
                .conditioner(Boolean.parseBoolean(parameters.get(CAR_CONDITIONER)))
                .transmissionType(Car.TransmissionType.valueOf(parameters.get(CAR_TRANSMISSION)))
                .carStatus(Car.CarStatus.valueOf(parameters.get(CAR_STATUS)))
                .registrationNumber(parameters.get(CAR_REGISTRATION_NUMBER))
                .cost(BigDecimal.valueOf(Long.parseLong(parameters.get(CAR_COST))))
                .build();
        create(car);
    }

    @Override
    public Optional<Car> findByRegistrationNumber(String regNumber) throws ServiceException {
        LOGGER.info("method findByRegistrationNumber() Car");
        final Optional<Car> optionalCar;
        try {
            optionalCar = carDaoImpl.findByRegistrationNumber( regNumber);
        } catch (DaoException e) {
            LOGGER.error("Error while find by RegistrationNumber [{}]", regNumber);
            throw new ServiceException(e);
        }
        return optionalCar;
    }
}
