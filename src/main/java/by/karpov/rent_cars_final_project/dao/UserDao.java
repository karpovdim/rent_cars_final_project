package by.karpov.rent_cars_final_project.dao;

import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * interface UserDao
 */
public interface UserDao {
    /**
     * find user by email
     *
     * @param email
     * @return optional of user
     * @throws DaoException
     */
    Optional<User> findByEmail(String email) throws DaoException;

    /**
     * find user by id
     *
     * @param id
     * @returnoptional of user
     * @throws DaoException
     */
    Optional<User> findById(Long id) throws DaoException;

    /**
     * find user by password for authentication
     * @param code
     * @return optional of user
     * @throws DaoException
     */
    Optional<User> findByPasswordForAuthentication(String code) throws DaoException;

    /**
     * find user by email and password
     *
     * @param email
     * @param password
     * @return optional of user
     * @throws DaoException
     */
    Optional<User> findByEmailAndPassword(String email, String password) throws DaoException;

    /**
     * find users by limit
     *
     * @param leftBorder
     * @param numberOfLines
     * @return list of users
     * @throws DaoException
     */
    List<User> findByLimit(int leftBorder, int numberOfLines) throws DaoException;

    /**
     * find all users
     *
     * @return list of users
     * @throws DaoException
     */
    List<User> findAll() throws DaoException;

    /**
     * create new user
     *
     * @param user
     * @return new user
     * @throws DaoException
     */
    User create(User user) throws DaoException;

    /**
     * update user
     * @param user
     * @return update user
     * @throws DaoException
     */
    User update(User user) throws DaoException;

    /**
     * update password for authentication
     *
     * @param userId
     * @param code
     * @return boolean result
     * @throws DaoException
     */
    boolean updatePasswordForAuthentication(long userId, String code) throws DaoException;

    /**
     * registration user in system
     *
     * @param user
     * @param password
     * @param passwordForAuthentication
     * @return boolean result
     * @throws DaoException
     */
    boolean registration(User user, String password, String passwordForAuthentication) throws DaoException;

    /**
     * delete user bu user
     * @param user
     * @throws DaoException
     */
    void delete(User user) throws DaoException;

    /**
     * update password for user
     *
     * @param emailLogin
     * @param hashPassword
     * @return boolean result
     * @throws DaoException
     */
    boolean updatePassword(String emailLogin, String hashPassword) throws DaoException;
}
