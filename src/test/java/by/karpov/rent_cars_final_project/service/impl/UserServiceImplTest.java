package by.karpov.rent_cars_final_project.service.impl;

import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.controller.filter.dao.UserDao;
import by.karpov.rent_cars_final_project.controller.filter.dao.impl.UserDaoImpl;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.DaoException;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.UserService;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserServiceImplTest {
    User user;
    UserDao dao;
    UserService service;
    Map<String, String> parameters;

    @BeforeClass
    public void initialize() {
        dao = Mockito.mock(UserDaoImpl.class);
        Whitebox.setInternalState(UserDaoImpl.class, "instance", dao);
        service = UserServiceImpl.getInstance();
        parameters = new HashMap<>();
        parameters.put(RequestParameter.USER_EMAIL, "korozarecipient@gmail.com");
        parameters.put(RequestParameter.USER_PASSWORD, "12345");
        parameters.put(RequestParameter.USER_PASSWORD_FOR_AUTHENTICATION, "1");
        parameters.put(RequestParameter.USER_FIRST_NAME, "Jack");
        parameters.put(RequestParameter.USER_LAST_NAME, "London");
        parameters.put(RequestParameter.USER_PHONE_NUMBER, "296957458");
        User user = User.builder()
                .firstName("Sam")
                .lastName("Jons")
                .build();
    }

    @Test(description = "Testing method findAll")
    public void positiveRegisterUserTest() throws ServiceException, DaoException {
        when(dao.registration(Mockito.any(User.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(true);
        boolean actual = service.registerUser(parameters);
        Assert.assertTrue(actual);
    }

    @Test(description = "Positive testing method findByEmail")
    public void positiveFindByEmailTest() throws ServiceException, DaoException {
        String email = "korozarecipient@gmail.com";
        when(dao.findByEmail(email)).thenReturn(Optional.of(new User()));
        Optional<User> actual = service.findByEmail(email);
        Assert.assertTrue(actual.isPresent());
    }

    @DataProvider(name = "createDataToNegativeFindByEmailTest")
    public Object[] createDataToNegativeFindByEmailTest() {
        return new Object[]{"1@!.1", " ", "qwer", null};
    }

    @Test(description = "Negative testing method findByEmail", dataProvider = "createDataToNegativeFindByEmailTest")
    public void negativeFindByEmailTest(String email) throws ServiceException, DaoException {
        when(dao.findByEmail(email)).thenReturn(Optional.empty());
        Optional<User> actual = service.findByEmail(email);
        Assert.assertFalse(actual.isPresent());
    }

    @DataProvider(name = "createDataToNegativeUpdateFirstNameTest")
    public Object[] createDataToNegativeUpdateFirstNameTest() {
        return new Object[][] { { 1, "Пе1дро" }, { 5, "Anto nio" }, { 2, " " }, { 4, null } };
    }

    @Test(description = "Negative testing method updateFirstName", dataProvider = "createDataToNegativeUpdateFirstNameTest")
    public void negativeUpdateFirstNameTest(long userId, String name) throws DaoException, ServiceException {
        boolean actual = service.updateFirstName(userId, name);
        Assert.assertFalse(actual);
    }


    @AfterClass
    public void tierDown() {
        dao = null;
        service = null;
        parameters = null;
    }
}