package by.karpov.rent_cars_final_project.service.impl;

import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.controller.filter.dao.OrderDao;
import by.karpov.rent_cars_final_project.controller.filter.dao.impl.OrderDaoImpl;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.DaoException;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.OrderService;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    OrderDao dao;
    OrderService service;
    Map<String, String> parameters;

    @BeforeClass
    public void initialize() {
        dao = mock(OrderDaoImpl.class);
        Whitebox.setInternalState(OrderDaoImpl.class, "instance", dao);
        service = new OrderServiceImpl(new UserServiceImpl(),new CarServiceImpl());
        parameters = new HashMap<>();
        parameters.put(RequestParameter.RENT_DATE, "2021-10-20");
        parameters.put(RequestParameter.RETURN_DATE, "2021-10-25");
        parameters.put(RequestParameter.CAR_ID, "12");
        parameters.put(RequestParameter.USER_ID, "35");
        parameters.put(RequestParameter.CAR_COST, "100");
    }



    @DataProvider(name = "dataToFindById")
    public Object[][] createDataToFindById() {
        return new Object[][] { { 3,  Order.builder()
                .id(3L)
                .status(Order.OrderStatus.PAID)
                .car(new Car(12L))
                .user(new User(35L))
                .build() },
                { 20,  Order.builder()
                        .id(20L)
                        .status(Order.OrderStatus.DECLINED)
                        .car(new Car(12L))
                        .user(new User(35L))
                        .build() } };
    }

    @Test(description = "Testing method findById", dataProvider = "dataToFindById")
    public void findById(long id, Order expected) throws DaoException, ServiceException {
        when(dao.findById(id)).thenReturn(Optional.of(expected));
        Optional<Order> actual = service.findById(id);
        Assert.assertEquals(actual, Optional.of(expected));
    }


    @AfterClass
    public void tierDown() {
        dao = null;
        service = null;
        parameters = null;
    }

}