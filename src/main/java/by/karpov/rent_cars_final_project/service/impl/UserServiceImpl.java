package by.karpov.rent_cars_final_project.service.impl;

import by.karpov.rent_cars_final_project.controller.filter.dao.UserDao;
import by.karpov.rent_cars_final_project.controller.filter.dao.impl.UserDaoImpl;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.DaoException;
import by.karpov.rent_cars_final_project.exception.NotFoundException;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.UserService;
import by.karpov.rent_cars_final_project.util.HashGenerator;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.karpov.rent_cars_final_project.command.RequestParameter.*;
import static by.karpov.rent_cars_final_project.entity.User.UserRole.CLIENT;
import static by.karpov.rent_cars_final_project.entity.User.UserStatus.CONFIRMATION_AWAITING;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final InputDataValidator validator = InputDataValidator.getInstance();

    public UserServiceImpl() {
    }

    @Override
    public boolean registerUser(Map<String, String> parameters) throws ServiceException {
        boolean result = false;
        final var email = parameters.get(USER_EMAIL);
        final var password = parameters.get(USER_PASSWORD);
        final var lastName = parameters.get(USER_LAST_NAME);
        final var firstName = parameters.get(USER_FIRST_NAME);
        final var phoneNumber = parameters.get(USER_PHONE_NUMBER);
        final var passwordForAuthentication = parameters.get(USER_PASSWORD_FOR_AUTHENTICATION);
        if (validator.isUserValid(email, password, firstName, lastName, phoneNumber)) {
            final var hashGenerator = HashGenerator.getInstance();
            final var hashPassword = hashGenerator.generatePasswordHash(password);
            final var user = User.builder()
                    .userStatus(CONFIRMATION_AWAITING)
                    .phoneNumber(phoneNumber)
                    .firstName(firstName)
                    .lastName(lastName)
                    .emailLogin(email)
                    .role(CLIENT)
                    .build();
            try {
                result = userDao.registration(user, hashPassword, passwordForAuthentication);
            } catch (DaoException e) {
                LOGGER.error("Exception in method registerUser()", e);
                throw new ServiceException("Exception in method registerUser()", e);
            }
        }
        return result;
    }

    @Override
    public Optional<User> findById(long id) throws ServiceException {
        if (!validator.isIdValid(id)) {
            return Optional.empty();
        }
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Exception while find by id user - [{}]", id, e);
            throw new ServiceException("Exception while find by id user ", e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            final var users = userDao.findAll();
            return List.copyOf(users);
        } catch (DaoException e) {
            LOGGER.error("Exception while findAll users ", e);
            throw new ServiceException("Exception while findAll users ", e);
        }
    }


    @Override
    public User update(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (DaoException e) {
            LOGGER.error("Exception while updating user -  [{}]", user, e);
            throw new ServiceException("Exception while updating user ", e);
        }
    }

    @Override
    public void delete(User user) throws ServiceException {
        try {
            userDao.delete(user);
        } catch (DaoException e) {
            LOGGER.error("Exception while deleting  user - [{}]", user, e);
            throw new ServiceException("Exception while deleting  user ", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws ServiceException {
        if (!validator.isEmailValid(email)) {
            return Optional.empty();
        }
        try {
            return userDao.findByEmail(email);
        } catch (DaoException e) {
            LOGGER.error("Exception while find user by email - [{}]", email, e);
            throw new ServiceException("Exception while find user by email - [{}]", e);
        }
    }

    @Override
    public Optional<User> findByPasswordForAuthentication(String code) throws ServiceException {
        if (!validator.isCodeValid(code)) {
            return Optional.empty();
        }
        try {
            return userDao.findByPasswordForAuthentication(code);
        } catch (DaoException e) {
            LOGGER.error("Exception while find user by code Authentication [{}]", code, e);
            throw new ServiceException("Exception while find user by code Authentication ", e);
        }
    }


    @Override
    public boolean updatePasswordForAuthentication(long userId, String code) throws ServiceException {
        if (!validator.isIdValid(userId)) {
            return false;
        }
        try {
            return userDao.updatePasswordForAuthentication(userId, code);
        } catch (DaoException e) {
            LOGGER.error("Exception while update user code  Authentication [{}]", code, e);
            throw new ServiceException("Exception while update user code  Authentication ", e);
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) throws ServiceException {
        if (validator.isEmailValid(email) && validator.isPasswordValid(password)) {
            final var hashGenerator = HashGenerator.getInstance();
            password = hashGenerator.generatePasswordHash(password);
            try {
                return userDao.findByEmailAndPassword(email, password);
            } catch (DaoException e) {
                LOGGER.error("Exception in method findByEmailAndPassword()", e);
                throw new ServiceException("Exception when find user by email and password", e);
            }
        }
        return Optional.empty();
    }

    @Override
    public double countUsers() throws ServiceException {
        try {
            final var users = userDao.findAll();
            return users.size();
        } catch (DaoException e) {
            LOGGER.error("Exception in method countUsers()", e);
            throw new ServiceException("Exception in method countUsers()", e);
        }
    }

    @Override
    public List<User> findByLimit(int leftBorder, int numberOfLines) throws ServiceException {
        try {
            return userDao.findByLimit(leftBorder, numberOfLines);
        } catch (DaoException e) {
            LOGGER.error("Exception in method findByLimit()", e);
            throw new ServiceException("Exception when find user by limit", e);
        }
    }

    @Override
    public Optional<User> updateStatus(long userId, User.UserStatus userStatus) throws ServiceException {
        if (validator.isIdValid(userId) && findById(userId).isPresent()) {
            final var user = findById(userId).get();
            user.setUserStatus(userStatus);
            final var updateUser = update(user);
            return Optional.of(updateUser);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> updateRole(long userId, User.UserRole userRole) throws ServiceException {
        if (validator.isIdValid(userId) && findById(userId).isPresent()) {
            final var user = findById(userId).get();
            user.setRole(userRole);
            final var updateUser = update(user);
            return Optional.of(updateUser);
        }
        return Optional.empty();
    }

    @Override
    public boolean updateFirstName(Long id, String userFirstname) {
        if (validator.isIdValid(id) && validator.isNameValid(userFirstname)) {
            try {
                var user = findById(id).orElseThrow(() -> new NotFoundException(id, "not found user by id"));
                user.setFirstName(userFirstname);
                update(user);
                return true;
            } catch (ServiceException | NotFoundException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateLastName(Long id, String userLastname) {
        if (validator.isIdValid(id) && validator.isNameValid(userLastname)) {
            try {
                var user = findById(id).orElseThrow(() -> new NotFoundException(id, "not found user by id"));
                user.setFirstName(userLastname);
                update(user);
                return true;
            } catch (ServiceException | NotFoundException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updatePhoneNumber(Long id, String phoneNumber) {
        if (validator.isIdValid(id) && validator.isPhoneNumberValid(phoneNumber)) {
            try {
                var user = findById(id).orElseThrow(() -> new NotFoundException(id, "not found user by id"));
                user.setFirstName(phoneNumber);
                update(user);
                return true;
            } catch (ServiceException | NotFoundException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateEmail(Long id, String email, String newEmail, String password) {
        if (validator.isEmailValid(newEmail) &&validator.isEmailValid(email) && validator.isPasswordValid(password)) {
            try {
                var userOptional = findByEmailAndPassword(email,password);
                if (userOptional.isPresent()) {
                    var user = userOptional.get();
                    user.setEmailLogin(newEmail);
                    update(user);
                    return true;
                }
            } catch (ServiceException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updatePassword(String emailLogin, String password, String newPassword) {
        InputDataValidator validator = InputDataValidator.getInstance();
        if (validator.isPasswordValid(password) && validator.isPasswordValid(newPassword)) {
            try {
                Optional<User> user = findByEmailAndPassword(emailLogin, password);
                if (user.isPresent()) {
                    HashGenerator hashGenerator = HashGenerator.getInstance();
                    String hashPassword = hashGenerator.generatePasswordHash(newPassword);
                    return userDao.updatePassword(emailLogin, hashPassword);
                }
            } catch (DaoException | ServiceException e) {
                return false;
            }
        }
        return false;
    }
}
