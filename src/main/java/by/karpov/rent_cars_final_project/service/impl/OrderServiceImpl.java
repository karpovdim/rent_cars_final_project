package by.karpov.rent_cars_final_project.service.impl;

import by.karpov.rent_cars_final_project.controller.filter.dao.CarDao;
import by.karpov.rent_cars_final_project.controller.filter.dao.OrderDao;
import by.karpov.rent_cars_final_project.controller.filter.dao.UserDao;
import by.karpov.rent_cars_final_project.controller.filter.dao.impl.CarDaoImpl;
import by.karpov.rent_cars_final_project.controller.filter.dao.impl.OrderDaoImpl;
import by.karpov.rent_cars_final_project.controller.filter.dao.impl.UserDaoImpl;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.exception.DaoException;
import by.karpov.rent_cars_final_project.exception.NotFoundException;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.CarService;
import by.karpov.rent_cars_final_project.service.OrderService;
import by.karpov.rent_cars_final_project.service.UserService;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.karpov.rent_cars_final_project.command.RequestParameter.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderDao orderDao = OrderDaoImpl.getInstance();
    private final InputDataValidator validator = InputDataValidator.getInstance();
private final UserService userService;
private final CarService carService;

    public OrderServiceImpl(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }



    @Override
    public Optional<Order> findById(long id) throws ServiceException {
        try {
         var   optionalOrder = orderDao.findById(id);
          return setUserAndCarById(optionalOrder);
        } catch (DaoException e) {
            LOGGER.error("Error while find by id task [{}]", id);
            throw new ServiceException(e);
        }
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

    @Override
    public double countOrders(long userId) throws ServiceException {
        try {
            final var count = orderDao.findAll().stream()
                    .filter(order -> order.getUser().getId() == userId)
                    .count();
            return (double)count;
        } catch (DaoException e) {
            LOGGER.error("Exception in method countOrders() with user id", e);
            throw new ServiceException("Exception in method countOrders() with user id", e);
        }
    }

    @Override
    public long add(Map<String, String> parameters) throws ServiceException {
        CarDao carDao = CarDaoImpl.getInstance();
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            LocalDate rentDate = LocalDate.parse(parameters.get(RENT_DATE));
            LocalDate returnDate = LocalDate.parse(parameters.get(RETURN_DATE));
            long carId = Long.parseLong(parameters.get(CAR_ID));
            long userId = Long.parseLong(parameters.get(USER_ID));
            final var order = Order.builder()
                    .car(carDao.findById(carId).orElseThrow(() -> new NotFoundException(carId)))
                    .user(userDao.findById(userId).orElseThrow(() -> new NotFoundException(userId)))
                    .rentDate(rentDate)
                    .returnDate(returnDate)
                    .status(Order.OrderStatus.AWAITS_PAYMENT)
                    .build();
            final var newOrder = orderDao.create(order);

            final var orderId = newOrder.getId();
            if(orderId!=null){
                final var car = carDao.findById(carId).get();
                car.setCarStatus(Car.CarStatus.BOOKED);
                carDao.update(car);
            }
            return orderId;
        } catch (DaoException | NotFoundException e) {
            LOGGER.error("exception in method add()", e);
            throw new ServiceException("Exception when add order and return id", e);
        }
    }

    @Override
    public List<Order> findByCarId(Long carId) throws ServiceException {
        try {
            return orderDao.findAll().stream()
                    .filter(id -> id.getCar().getId().equals(carId))
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            LOGGER.error("exception in method findByCarId()", e);
            throw new ServiceException("Exception when find orders by car id", e);
        }
    }

    @Override
    public List<Order> findByUserIdAndLimit(Long userId, int leftBorderCars, int limitOrdersOnPage) throws ServiceException {
            LOGGER.info("method findByUserIdAndLimit()");
            try {
                final var orders = orderDao.findByUserIdAndLimit(userId, leftBorderCars, limitOrdersOnPage);
                return listOrdersFillingCarsAndUsers(orders);
            } catch (DaoException e) {
                LOGGER.error("exception in method findByUserIdAndLimit()", e);
                throw new ServiceException("Exception when find orders by user id and limit", e);
        }
    }

    private List<Order> listOrdersFillingCarsAndUsers(List<Order> orders){
        final var listProcessing = orders.stream()
                .map(Optional::of)
                .map(order -> uncheckCall(() -> setUserAndCarById(order)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return List.copyOf(listProcessing);
    }

    private Optional<Order> setUserAndCarById(Optional<Order> optionalOrder) throws ServiceException {

        try{
            if (optionalOrder.isPresent()) {
                final var order = optionalOrder.get();
                final var carId = order.getCar().getId();
                final var car = carService.findById(carId).orElseThrow(() -> new NotFoundException(carId));
                final var userId = order.getUser().getId();
                final var user = userService.findById(userId).orElseThrow(() -> new NotFoundException(userId));
                order.setCar(car);
                order.setUser(user);
                return Optional.of(order);}
        }catch (NotFoundException e) {
            throw new ServiceException(e);
        }
        return Optional.empty();
    }
}
