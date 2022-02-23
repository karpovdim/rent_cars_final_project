package by.karpov.rent_cars_final_project.controller.filter.dao.impl;

import by.karpov.rent_cars_final_project.controller.filter.dao.OrderDao;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.DaoException;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.karpov.rent_cars_final_project.controller.filter.dao.impl.QueryDao.*;
import static by.karpov.rent_cars_final_project.pool.ConnectionPool.pool;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class OrderDaoImpl implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger(OrderDaoImpl.class);
    private static final OrderDao instance = new OrderDaoImpl();
    private OrderDaoImpl() {
    }

    public static OrderDao getInstance() {
        return instance;
    }

    @Override
    public Optional<Order> findById(Long id) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_BY_ID_ORDER)
        ) {
            statement.setLong(1, id);
            try (final var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final var order = buildOrder(resultSet);
                    return Optional.of(order);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while find by id task [{}]", id, e);
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findByLimit(int leftBorder, int numberOfLines) throws DaoException {
        final var orders = new ArrayList<Order>();
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_ORDERS_BY_LIMIT)) {
            statement.setInt(1, leftBorder);
            statement.setInt(2, numberOfLines);
            try (final var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(buildOrder(resultSet));
                }
            } catch (SQLException e) {
                LOGGER.error("exception in method findByLimit()", e);
                throw new DaoException("Exception when find orders by limit", e);
            }
            return List.copyOf(orders);
        } catch (SQLException e) {
            LOGGER.error("exception in method findByLimit()", e);
            throw new DaoException("Exception when find orders by limit", e);
        }
    }

    @Override
    public List<Order> findAll() throws DaoException {
        final var orders = new ArrayList<Order>();
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_ALL_ORDERS);
             final var resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while findAll Order ", e);
            throw new DaoException("Exception while findAll Order ", e);
        }
        return List.copyOf(orders);
    }

    @Override
    public Order create(Order order) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(INSERT_ORDER, RETURN_GENERATED_KEYS)) {
            statementOrderSetParameters(order, statement);
            statement.executeUpdate();
            try(var resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    final long id = resultSet.getLong(1);
                    order.setId(id);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while creating Order [{}]", order, e);
            throw new DaoException("Exception while creating Order ", e);
        }
        return order;
    }

    @Override
    public void delete(Order order) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(DELETE_ORDER)) {
            statement.setLong(1, order.getId());
            final var count = statement.executeUpdate();
            if (count > 1) {
                LOGGER.error("Exception while deleting Order - [{}]", order);
                throw new DaoException("Exception while deleting Order");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while deleting task Order -[{}]", order, e);
            throw new DaoException("Exception while deleting Order");
        }
    }

    @Override
    public List<Order> findByUserIdAndLimit(Long userId, int leftBorderCars, int limitOrdersOnPage) throws DaoException {
        final var orders = new ArrayList<Order>();
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(FIND_ORDERS_BY_USER_ID_AND_LIMIT)) {
            statement.setLong(1,userId);
            statement.setInt(2, leftBorderCars);
            statement.setInt(3, limitOrdersOnPage);
            try (final var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(buildOrder(resultSet));
                }
            } catch (SQLException e) {
                LOGGER.error("exception in method findByLimit() with user id", e);
                throw new DaoException("Exception when find orders by limit with user id", e);
            }
            return List.copyOf(orders);
        } catch (SQLException e) {
            LOGGER.error("exception in method findByLimit()", e);
            throw new DaoException("Exception when find orders by limit", e);
        }
    }

    @Override
    public Order update(Order order) throws DaoException {
        try (final var connection = pool().getConnection();
             final var statement = connection.prepareStatement(UPDATE_ORDER)) {
            statementOrderSetParameters(order, statement);
            statement.setLong(7, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while updating Order [{}]", order, e);
            throw new DaoException("Exception while updating Order ", e);
        }
        return order;
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException{
        return Order.builder()
                .id(resultSet.getLong(COLUMN_ID))
                .price(resultSet.getBigDecimal(COLUMN_PRICE_ORDER))
                .rentDate(resultSet.getObject(COLUMN_RENT_DATE_ORDER, LocalDate.class))
                .returnDate(resultSet.getObject(COLUMN_RETURN_DATE_ORDER, LocalDate.class))
                .car(new Car(resultSet.getLong(COLUMN_ORDER_CAR_ID)))
                .user(new User(resultSet.getLong(COLUMN_ORDER_USER_ID)))
                .status(Order.OrderStatus.valueOf(resultSet.getString(COLUMN_ORDER_STATUS_ID)))
                .build();
    }

    private void statementOrderSetParameters(Order order, PreparedStatement statement) throws SQLException {
        statement.setBigDecimal(1, order.getPrice());
        statement.setObject(2, order.getRentDate());
        statement.setObject(3, order.getReturnDate());
        statement.setLong(4, order.getCar().getId());
        statement.setLong(5, order.getUser().getId());
        statement.setLong(6, order.getStatus().ordinal() + 1);
    }
}
