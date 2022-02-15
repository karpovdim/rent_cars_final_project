package by.karpov.rent_cars_final_project.controller.filter.dao.impl;

import by.karpov.rent_cars_final_project.controller.filter.dao.UserDao;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.DaoException;
import by.karpov.rent_cars_final_project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.karpov.rent_cars_final_project.controller.filter.dao.impl.QueryDao.*;
import static by.karpov.rent_cars_final_project.pool.ConnectionPool.pool;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private static final UserDao instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDao getInstance() {
        return instance;
    }

    @Override
    public boolean registration(User user, String password, String passwordForAuthentication) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(INSERT_USER, RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmailLogin());
            statement.setString(4, password);
            statement.setLong(5, user.getRole().ordinal() + 1);
            statement.setLong(6, user.getUserStatus().ordinal() + 1);
            statement.setString(7, user.getPhoneNumber());
            statement.setString(8, passwordForAuthentication);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Exception while creating User - [{}]", user, e);
            throw new DaoException("Exception while creating User - [{}]",e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_BY_EMAIL_USER)
        ) {
            statement.setString(1, email);
            try (final var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final var car = buildUser(resultSet);
                    return Optional.of(car);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while find by email User [{}]", email, e);
            throw new DaoException("Exception while find by email User [{}]",e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_BY_ID_USER)
        ) {
            statement.setLong(1, id);
            try (final var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final var car = buildUser(resultSet);
                    return Optional.of(car);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while find by id User [{}]", id, e);
            throw new DaoException("Exception while find User by id ",e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws DaoException {
        final var users = new ArrayList<User>();
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_ALL_USER);
             final var resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while findAll User ", e);
            throw new DaoException("Exception while findAll User ",e);
        }
        return List.copyOf(users);
    }

    @Override
    public boolean updatePassword(String emailLogin, String hashPassword) throws DaoException {
        LOGGER.info("method updatePassword()");
        boolean result = false;
        try (Connection connection = ConnectionPool.pool().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            statement.setString(2, emailLogin);
            statement.setString(1, hashPassword);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("exception in method updatePassword()", e);
            throw new DaoException("Exception when update password", e);
        }
        return result;
    }


    @Override
    public void delete(User user) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(DELETE_USER)) {
            statement.setLong(1, user.getId());
            final int count = statement.executeUpdate();
            if (count > 1) {
                LOGGER.error("Exception while deleting User - [{}]", user);
                throw new DaoException("Exception while deleting ");

            }
        } catch (SQLException e) {
            LOGGER.error("Exception while deleting User with [{}]", user, e);
            throw new DaoException("Exception while deleting User ", e);
        }
    }

    @Override
    public User create(User user) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(INSERT_USER, RETURN_GENERATED_KEYS);
             final var resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
                final long id = resultSet.getLong(COLUMN_ID);
                user.setId(id);
            }
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmailLogin());
            statement.setLong(4, user.getRole().ordinal() + 1);
            statement.setLong(5, user.getUserStatus().ordinal() + 1);
            statement.setString(6, user.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while creating User - [{}]", user, e);
            throw new DaoException("Exception while creating User ", e);
        }
        return user;
    }

    @Override
    public User update(User user) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmailLogin());
            statement.setLong(4, user.getRole().ordinal() + 1);
            statement.setLong(5, user.getUserStatus().ordinal() + 1);
            statement.setString(6, user.getPhoneNumber());
            statement.setLong(7, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while updating User - [{}]", user, e);
            throw new DaoException("Exception while updating User ", e);
        }
        return user;
    }

    @Override
    public Optional<User> findByPasswordForAuthentication(String code) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_BY_CODE_AUTHENTICATION_USER)
        ) {
            statement.setString(1, code);
            try (final var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final var user = buildUser(resultSet);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while find by code Authentication User - [{}]", code, e);
            throw new DaoException("Exception while find by code Authentication User ", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean updatePasswordForAuthentication(long userId, String code) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(SQL_UPDATE_CODE)) {
            statement.setString(1, code);
            statement.setLong(2, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("exception in method updateStatus()", e);
            throw new DaoException("Exception when update status", e);
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) throws DaoException {
        try (final var connection = pool().getConnection();
              var statement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD_USER)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (final var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final var user = buildUser(resultSet);
                    return Optional.of(user);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("Exception while find by email and password task [{}]", email);
            throw new DaoException("Exception while find by email and password task ", ex);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findByLimit(int leftBorder, int numberOfLines) throws DaoException {
        final var users = new ArrayList<User>();
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_USERS_BY_LIMIT)) {
            statement.setInt(1, leftBorder);
            statement.setInt(2, numberOfLines);
            try (final var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(buildUser(resultSet));
                }
            } catch (SQLException e) {
                LOGGER.error("exception in method findByLimit()", e);
                throw new DaoException("Exception when find users by limit", e);
            }
            return List.copyOf(users);
        } catch (SQLException e) {
            LOGGER.error("exception in method findByLimit()", e);
            throw new DaoException("Exception when find users by limit", e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong(COLUMN_ID))
                .firstName(resultSet.getString(COLUMN_FIRST_NAME))
                .lastName(resultSet.getString(COLUMN_LAST_NAME))
                .emailLogin(resultSet.getString(COLUMN_EMAIL_LOGIN))
                .role(User.UserRole.valueOf(resultSet.getString(COLUMN_ROLE_USER)))
                .userStatus(User.UserStatus.valueOf(resultSet.getString(COLUMN_STATUS_USER)))
                .phoneNumber(resultSet.getString(COLUMN_PHONE_USER))
                .build();
    }
}

