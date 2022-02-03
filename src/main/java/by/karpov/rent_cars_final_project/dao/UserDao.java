package by.karpov.rent_cars_final_project.dao;

import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findByEmail(String email) throws DaoException;

    Optional<User> findById(Long id) throws DaoException;

    Optional<User> findByPasswordForAuthentication(String code) throws DaoException;

    Optional<User> findByEmailAndPassword(String email, String password) throws DaoException;

    List<User> findByLimit(int leftBorder, int numberOfLines) throws DaoException;

    List<User> findAll() throws DaoException;

    User create(User user) throws DaoException;

    User update(User user) throws DaoException;

    boolean updatePasswordForAuthentication(long userId, String code) throws DaoException;

    boolean registration(User user, String password, String passwordForAuthentication) throws DaoException;

    void delete(User user) throws DaoException;

    boolean updatePassword(String emailLogin, String hashPassword) throws DaoException;
}
