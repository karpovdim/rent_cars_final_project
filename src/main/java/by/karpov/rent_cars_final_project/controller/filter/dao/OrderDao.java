package by.karpov.rent_cars_final_project.controller.filter.dao;

import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * interface OrderDao
 */
public interface OrderDao {
    /**
     * find by limit
     * @param leftBorder
     * @param numberOfLines
     * @return list of orders
     * @throws DaoException
     */

    List<Order> findByLimit(int leftBorder, int numberOfLines) throws DaoException;

    /**
     * find all
     *
     * @return list of orders
     * @throws DaoException
     */

    List<Order> findAll() throws DaoException;

    /**
     * find by id
     * @param id
     * @return optional of order
     * @throws DaoException
     */
    Optional<Order> findById(Long id) throws DaoException;

    /**
     * create new order
     *
     * @param order
     * @return return new order
     * @throws DaoException
     */
    Order create(Order order) throws DaoException;

    /**
     * update order
     *
     * @param order
     * @return update order
     * @throws DaoException
     */
    Order update(Order order) throws DaoException;

    /**
     * delete order
     *
     * @param order
     * @throws DaoException
     */
    void delete(Order order) throws DaoException;

    /**
     * find by user id and limit
     * @param userId
     * @param leftBorderCars
     * @param limitOrdersOnPage
     * @return list of order
     * @throws DaoException
     */
    List<Order> findByUserIdAndLimit(Long userId, int leftBorderCars, int limitOrdersOnPage) throws DaoException;

}
