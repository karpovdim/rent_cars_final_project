package by.karpov.rent_cars_final_project.dao.impl;

import by.karpov.rent_cars_final_project.dao.InsuranceDao;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.entity.Insurance;
import by.karpov.rent_cars_final_project.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.karpov.rent_cars_final_project.dao.impl.QueryDao.*;
import static by.karpov.rent_cars_final_project.pool.ConnectionPool.pool;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class InsuranceDaoImpl implements InsuranceDao {
    private static final Logger LOGGER = LogManager.getLogger(InsuranceDaoImpl.class);
    private static final InsuranceDao instance = new InsuranceDaoImpl();

    private InsuranceDaoImpl() {
    }

    public static InsuranceDao getInstance() {
        return instance;
    }

    @Override
    public Optional<Insurance> findById(Long id) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_BY_ID_INSURANCE)) {
            statement.setLong(1, id);
            try (final var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final var insurance = buildInsurance(resultSet);
                    return Optional.of(insurance);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while find Insurance by id - [{}]", id, e);
            throw new DaoException("Exception while find Insurance by id ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Insurance> findAll() throws DaoException {
        final var insurances = new ArrayList<Insurance>();
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_ALL_INSURANCE)) {
            try (final var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    insurances.add(buildInsurance(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while find all insurances ", e);
            throw new DaoException("Exception while find all insurances ", e);
        }
        return List.copyOf(insurances);
    }

    @Override
    public Insurance create(Insurance insurance) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(INSERT_INSURANCE, RETURN_GENERATED_KEYS);
             final var resultSet = statement.getGeneratedKeys()
        ) {
            if (resultSet.next()) {
                final var id = resultSet.getLong(COLUMN_ID);
                insurance.setId(id);
            }
            statementInsuranceSetParameters(insurance, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while creating insurance [{}]", insurance, e);
            throw new DaoException("Exception while creating insurance ", e);
        }
        return insurance;
    }

    @Override
    public void delete(Insurance insurance) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(DELETE_INSURANCE)) {
            statement.setLong(1, insurance.getId());
            final var count = statement.executeUpdate();
            if (count > 1) {
                LOGGER.error("Exception while deleting insurance [{}]", insurance);
                throw new DaoException("Exception while deleting insurance [{}]");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while deleting insurance [{}]", insurance, e);
            throw new DaoException("Exception while deleting insurance ", e);

        }
    }

    @Override
    public Insurance update(Insurance insurance) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(UPDATE_INSURANCE)) {
            statementInsuranceSetParameters(insurance, statement);
            statement.setLong(6, insurance.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while updating insurance [{}]", insurance, e);
            throw new DaoException("Exception while updating insurance ", e);
        }
        return insurance;
    }

    private Insurance buildInsurance(ResultSet resultSet) throws SQLException {
        return Insurance.builder()
                .id(resultSet.getLong(COLUMN_ID))
                .startInsurance(resultSet.getObject(COLUMN_DATE_START, LocalDateTime.class))
                .endInsurance(resultSet.getObject(COLUMN_DATE_END, LocalDateTime.class))
                .isActive(resultSet.getBoolean(COLUMN_IS_ACTIVE))
                .identificationNumber(resultSet.getLong(COLUMN_IDENTIFICATION_NUMBER))
                .car(new Car(resultSet.getLong(COLUMN_CAR_ID)))// TODO FIND BY ID IN SERVICE
                .build();
    }

    private void statementInsuranceSetParameters(Insurance insurance, PreparedStatement statement) throws SQLException {
        statement.setObject(1, insurance.getStartInsurance());
        statement.setObject(2, insurance.getEndInsurance());
        statement.setBoolean(3, insurance.getIsActive());
        statement.setLong(4, insurance.getIdentificationNumber());
        statement.setLong(5, insurance.getCar().getId());
    }
}
