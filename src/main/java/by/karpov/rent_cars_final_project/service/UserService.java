package by.karpov.rent_cars_final_project.service;

import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean registerUser(Map<String, String> parameters) throws ServiceException;

    Optional<User> findById(long id) throws ServiceException;

    List<User> findAll() throws ServiceException;

    User update(User user) throws ServiceException;

    void delete(User user) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;

    Optional<User> findByPasswordForAuthentication(String code) throws ServiceException;

    boolean updatePasswordForAuthentication(long userId, String code) throws ServiceException;

    Optional<User> findByEmailAndPassword(String email, String password) throws ServiceException;

    double countUsers() throws ServiceException;

    List<User> findByLimit(int leftBorder, int numberOfLines) throws ServiceException;

    Optional<User> updateStatus(long userId, User.UserStatus userStatus) throws ServiceException;

    Optional<User> updateRole(long userId, User.UserRole userRole) throws ServiceException ;

    boolean updateFirstName(Long id, String userFirstname);

    boolean updateLastName(Long id, String userLastname);

    boolean updatePhoneNumber(Long id, String phoneNumber);

    boolean updateEmail(Long id, String newEmail, String email, String password);

    boolean updatePassword(String emailLogin, String password, String newPassword);
}
