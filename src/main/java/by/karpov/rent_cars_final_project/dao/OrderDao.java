package by.karpov.rent_cars_final_project.dao;

import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> findByLimit(int leftBorder, int numberOfLines) throws DaoException;

    List<Order> findAll() throws DaoException;

    Optional<Order> findById(Long id) throws DaoException;

    Order create(Order order) throws DaoException;

    Order update(Order order) throws DaoException;

    void delete(Order order) throws DaoException;
}
