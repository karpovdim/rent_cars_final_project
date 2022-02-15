package by.karpov.rent_cars_final_project.service;

import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * UserService interface
 */
public interface UserService {
    /**
     * registration user
     * @param parameters
     * @return boolean result
     * @throws ServiceException
     */
    boolean registerUser(Map<String, String> parameters) throws ServiceException;

    /**
     * find user by id
     * @param id
     * @return optional of user
     * @throws ServiceException
     */
    Optional<User> findById(long id) throws ServiceException;

    /**
     * find all users
     * @return list of all users
     * @throws ServiceException
     */
    List<User> findAll() throws ServiceException;

    /**
     * update user
     * @param user
     * @return return user of update
     * @throws ServiceException
     */
    User update(User user) throws ServiceException;

    /**
     * delete user
     * @param user
     * @throws ServiceException
     */
    void delete(User user) throws ServiceException;

    /**
     * find user by email
     * @param email
     * @return optional of user
     * @throws ServiceException
     */
    Optional<User> findByEmail(String email) throws ServiceException;

    /**
     * find user by password for authenticate
     * @param code
     * @return optional of user
     * @throws ServiceException
     */
    Optional<User> findByPasswordForAuthentication(String code) throws ServiceException;

    /**
     * update password for authenticate
     * @param userId
     * @param code
     * @return boolean result
     * @throws ServiceException
     */
    boolean updatePasswordForAuthentication(long userId, String code) throws ServiceException;

    /**
     * find user by email and password
     * @param email
     * @param password
     * @return optional of user
     * @throws ServiceException
     */
    Optional<User> findByEmailAndPassword(String email, String password) throws ServiceException;

    /**
     * count user
     * @return double of count all user
     * @throws ServiceException
     */
    double countUsers() throws ServiceException;

    /**
     * find all user by limit
     * @param leftBorder
     * @param numberOfLines
     * @return list of users
     * @throws ServiceException
     */
    List<User> findByLimit(int leftBorder, int numberOfLines) throws ServiceException;

    /**
     * update user status
     * @param userId
     * @param userStatus
     * @return optional of update user
     * @throws ServiceException
     */
    Optional<User> updateStatus(long userId, User.UserStatus userStatus) throws ServiceException;

    /**
     * update user role
     * @param userId
     * @param userRole
     * @return
     * @throws ServiceException
     */
    Optional<User> updateRole(long userId, User.UserRole userRole) throws ServiceException ;

    /**
     * update user first name
     * @param id
     * @param userFirstname
     * @return boolean result
     */
    boolean updateFirstName(Long id, String userFirstname);

    /**
     * update user last name
     * @param id
     * @param userLastname
     * @return boolean result
     */
    boolean updateLastName(Long id, String userLastname);

    /**
     * update user phone number
     * @param id
     * @param phoneNumber
     * @return boolean result
     */
    boolean updatePhoneNumber(Long id, String phoneNumber);

    /**
     * update user email
     * @param id
     * @param newEmail
     * @param email
     * @param password
     * @return boolean result
     */
    boolean updateEmail(Long id, String newEmail, String email, String password);

    /**
     * update password
     * @param emailLogin
     * @param password
     * @param newPassword
     * @return boolean result
     */
    boolean updatePassword(String emailLogin, String password, String newPassword);
}
