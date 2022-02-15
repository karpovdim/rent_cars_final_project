package by.karpov.rent_cars_final_project.service;

import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * OrderService interface
 */
public interface OrderService extends Base {
    /**
     * find order by id
     *
     * @param id
     * @return optional of order
     * @throws ServiceException
     */
    Optional<Order> findById(long id) throws ServiceException;

    /**
     * find order by id
     * @return list of orders
     * @throws ServiceException
     */
    List<Order> findAll() throws ServiceException;

    /**
     * create order
     * @param order
     * @return new order
     * @throws ServiceException
     */
    Order create(Order order) throws ServiceException;

    /**
     * update order
     * @param order
     * @return update order
     * @throws ServiceException
     */
    Order update(Order order) throws ServiceException;

    /**
     * delete order
     *
     * @param order
     * @throws ServiceException
     */
    void delete(Order order) throws ServiceException;

    /**
     * count of all orders
     *
     * @return double of all orders
     * @throws ServiceException
     */
    double countOrders() throws ServiceException;

    /**
     * count orders by user id
     * @param userId
     * @return double of all orders by user id
     * @throws ServiceException
     */
    double countOrders(long userId) throws ServiceException;

    /**
     * find orders by limit
     * @param leftBorderUsers
     * @param limitOrdersOnPage
     * @return list of orders by limit
     * @throws ServiceException
     */
    List<Order> findByLimit(int leftBorderUsers, int limitOrdersOnPage) throws ServiceException;

    /**
     * update order status
     * @param orderId
     * @param orderStatus
     * @return optional of order
     * @throws ServiceException
     */
    Optional<Order> updateStatus(long orderId, Order.OrderStatus orderStatus) throws ServiceException;

    /**
     * find order by status
     * @param orderStatus
     * @return list of orders
     * @throws ServiceException
     */
    List<Order> findByStatus(Order.OrderStatus orderStatus)throws ServiceException;

    /**
     * add new order
     * @param parameters
     * @return list of orders
     * @throws ServiceException
     */
    long add(Map<String, String> parameters) throws ServiceException;

    /**
     * find order by car id
     * @param id
     * @return list of orders
     * @throws ServiceException
     */
    List<Order> findByCarId(Long id) throws ServiceException;

    /**
     *  find all orders by user id
     * @param userId
     * @param leftBorderCars
     * @param limitOrdersOnPage
     * @return list of orders
     * @throws ServiceException
     */
    List<Order> findByUserIdAndLimit(Long userId, int leftBorderCars, int limitOrdersOnPage) throws ServiceException;

}
