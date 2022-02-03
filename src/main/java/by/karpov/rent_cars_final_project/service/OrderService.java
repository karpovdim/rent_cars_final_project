package by.karpov.rent_cars_final_project.service;

import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findById(long id) throws ServiceException;

    List<Order> findAll() throws ServiceException;

    Order create(Order order) throws ServiceException;

    Order update(Order order) throws ServiceException;

    void delete(Order order) throws ServiceException;

    double countOrders() throws ServiceException;

    List<Order> findByLimit(int leftBorderUsers, int limitOrdersOnPage) throws ServiceException;

    Optional<Order> updateStatus(long parseLong, Order.OrderStatus orderStatus) throws ServiceException;

    List<Order> findByStatus(Order.OrderStatus orderStatus)throws ServiceException;
}