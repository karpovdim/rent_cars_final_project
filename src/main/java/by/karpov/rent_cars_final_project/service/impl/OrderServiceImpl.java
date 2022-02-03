package by.karpov.rent_cars_final_project.service.impl;

import by.karpov.rent_cars_final_project.dao.OrderDao;
import by.karpov.rent_cars_final_project.dao.impl.CarDaoImpl;
import by.karpov.rent_cars_final_project.dao.impl.OrderDaoImpl;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.exception.DaoException;
import by.karpov.rent_cars_final_project.exception.NotFoundException;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.OrderService;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    private static final OrderServiceImpl INSTANCE = new OrderServiceImpl();
    private final OrderDao orderDao = OrderDaoImpl.getInstance();
    private final InputDataValidator validator = InputDataValidator.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Order> findById(long id) throws ServiceException {
        final Optional<Order> optionalOrder;
        try {
            optionalOrder = orderDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Error while find by id task [{}]", id);
            throw new ServiceException(e);
        }
        return optionalOrder;
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        final List<Order> orders;
        try {
            orders = orderDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Error while findAll task ");
            throw new ServiceException(e);
        }
        return List.copyOf(orders);
    }

    @Override
    public Order create(Order order) throws ServiceException {
        final Order createOrder;
        try {
            createOrder = orderDao.create(order);
        } catch (DaoException e) {
            LOGGER.error("Error while creating task [{}]", order);
            throw new ServiceException(e);
        }
        return createOrder;
    }

    @Override
    public Order update(Order order) throws ServiceException {
        final Order updateOrder;
        try {
            updateOrder = orderDao.update(order);
        } catch (DaoException e) {
            LOGGER.error("Error while deleting task [{}]", order);
            throw new ServiceException(e);

        }
        return updateOrder;
    }

    @Override
    public void delete(Order order) throws ServiceException {
        try {
            orderDao.delete(order);
        } catch (DaoException e) {
            LOGGER.error("Error while deleting task [{}]", order);
            throw new ServiceException(e);

        }
    }

    @Override
    public List<Order> findByLimit(int leftBorder, int numberOfLines) throws ServiceException {
        try {
            final var carDao = CarDaoImpl.getInstance();
            var byLimit = orderDao.findByLimit(leftBorder, numberOfLines);
            for (Order order : byLimit) {
                final var carId = order.getCar().getId();
                order.setCar(carDao.findById(carId).orElseThrow(() -> new NotFoundException(carId, " not found")));
            }
            return byLimit;
        } catch (DaoException | NotFoundException e) {
            LOGGER.error("Exception in method findByLimit()", e);
            throw new ServiceException("Exception when find order by limit", e);
        }
    }

    @Override
    public Optional<Order> updateStatus(long orderId, Order.OrderStatus orderStatus) throws ServiceException {
        try {
            if (validator.isIdValid(orderId) && findById(orderId).isPresent()) {
                final var order = findById(orderId).get();
                order.setStatus(orderStatus);
                final var updateOrder = update(order);
                final var carDao = CarDaoImpl.getInstance();
                final var carId = order.getCar().getId();
                updateOrder.setCar(carDao.findById(carId).orElseThrow(() -> new NotFoundException(carId, " not found")));
                return Optional.of(updateOrder);
            }
        } catch (DaoException | NotFoundException e) {
            LOGGER.error("Exception in method updateStatus()", e);
            throw new ServiceException("Exception when return order after update", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findByStatus(Order.OrderStatus orderStatus) throws ServiceException {
        final var orders = findAll().stream()
                .filter(order -> order.getStatus() == orderStatus)
                .collect(Collectors.toList());
        return List.copyOf(orders);
    }

    @Override
    public double countOrders() throws ServiceException {
        final double countOrders;
        try {
            countOrders = orderDao.findAll().size();
        } catch (DaoException e) {
            LOGGER.error("Exception in method countOrders()", e);
            throw new ServiceException("Exception in method countOrders()", e);
        }
        return countOrders;
    }
}
