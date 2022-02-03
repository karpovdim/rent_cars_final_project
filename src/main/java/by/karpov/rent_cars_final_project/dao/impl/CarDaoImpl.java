package by.karpov.rent_cars_final_project.dao.impl;

import by.karpov.rent_cars_final_project.dao.CarDao;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.karpov.rent_cars_final_project.dao.impl.QueryDao.*;
import static by.karpov.rent_cars_final_project.pool.ConnectionPool.pool;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class CarDaoImpl implements CarDao {
    private static final Logger LOGGER = LogManager.getLogger(CarDaoImpl.class);
    private static final CarDao instance = new CarDaoImpl();

    private CarDaoImpl() {
    }

    public static CarDao getInstance() {
        return instance;
    }

    @Override
    public Optional<Car> findById(Long id) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_BY_ID_CAR)
        ) {
            statement.setLong(1, id);
            try (final var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final var car = buildCar(resultSet);
                    return Optional.of(car);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while find by id Car [{}]", id, e);
            throw new DaoException("Error while find by id Car [{}]", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Car> findAll() throws DaoException {
        final var cars = new ArrayList<Car>();
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_ALL_CAR);
             final var resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cars.add(buildCar(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Error while findAll Car ", e);
            throw new DaoException("Error while findAll Car ", e);
        }
        return List.copyOf(cars);
    }

    @Override
    public Car update(Car car) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(UPDATE_CAR)) {
            statementCarSetParameters(car, statement);
            statement.setLong(8, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error while updating Car - [{}]", car, e);
            throw new DaoException("Error while updating Car ", e);
        }
        return car;
    }

    @Override
    public Car create(Car car) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(INSERT_CAR, RETURN_GENERATED_KEYS);
             final var resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
                final var id = resultSet.getLong(COLUMN_ID);
                car.setId(id);
            }
            statementCarSetParameters(car, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error while creating Car [{}]", car,e);
            throw new DaoException("Error while creating Car ",e);
        }
        return car;
    }

    @Override
    public void delete(Car car) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(DELETE_CAR)) {
            statement.setLong(1, car.getId());
            final int count = statement.executeUpdate();
            if (count > 1) {
                LOGGER.error("Error while deleting Car [{}]", car);
                throw new DaoException("Error while deleting Car");
            }
        } catch (SQLException e) {
            LOGGER.error("Error while deleting Car [{}]", car,e);
            throw new DaoException("Error while deleting Car ",e);
        }
    }

    @Override
    public Optional<Car> findByRegistrationNumber(String regNumber) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_BY_REGISTRATION_NUMBER_CAR)
        ) {
            statement.setString(1, regNumber);
            try (final var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final var car = buildCar(resultSet);
                    return Optional.of(car);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while find by registration number Car [{}]", regNumber, e);
            throw new DaoException("Error while find by registration number Car [{}]", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Car> findByLimit(int leftBorderCars, int limitCarsOnPage) throws DaoException {
        final var cars = new ArrayList<Car>();
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_CARS_BY_LIMIT)) {
            statement.setInt(1, leftBorderCars);
            statement.setInt(2, limitCarsOnPage);
            try (final var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cars.add(buildCar(resultSet));
                }
            } catch (SQLException e) {
                LOGGER.error("Exception find Cars by limit  ", e);
                throw new DaoException("Exception find Cars by limit ", e);
            }
            return cars;
        } catch (SQLException e) {
            LOGGER.error("Exception find Cars by limit", e);
            throw new DaoException("Exception find Cars by limit", e);
        }
    }

    private Car buildCar(ResultSet resultSet) throws SQLException {
        return Car.builder()
                .id(resultSet.getLong(COLUMN_ID))
                .registrationNumber(resultSet.getString(REGISTRATION_NUMBER))
                .cost(resultSet.getBigDecimal(COST_BY_CAR))
                .imageUrl(resultSet.getString(IMAGE_URL))
                .carStatus(Car.CarStatus.valueOf(resultSet.getString(CAR_STATUS)))
                .transmissionType(Car.TransmissionType.valueOf(resultSet.getString(TRANSMISSION_TYPE)))
                .conditioner(resultSet.getBoolean(CONDITIONER))
                .descriptionCar(resultSet.getString(CAR_DESCRIPTION))
                .build();
    }

    private void statementCarSetParameters(Car car, PreparedStatement statement) throws SQLException {
        statement.setString(1, car.getRegistrationNumber());
        statement.setBigDecimal(2, car.getCost());
        statement.setString(3, car.getImageUrl());
        statement.setLong(4, car.getCarStatus().ordinal() + 1);
        statement.setLong(5, car.getTransmissionType().ordinal() + 1);
        statement.setBoolean(6, car.isConditioner());
        statement.setString(7, car.getCarDescription());
    }
}
