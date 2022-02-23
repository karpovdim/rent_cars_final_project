package by.karpov.rent_cars_final_project.service.impl;

import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.exception.DaoException;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.CarService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class CarServiceImplTest {
    CarService service;
    Map<String, String> parameters;
    Car car = Car.builder()
            .carStatus(Car.CarStatus.CAR_IS_SERVICED)
            .build();

    @BeforeClass
    public void initialize() {
        service = new CarServiceImpl();
        parameters = new HashMap<>();
        parameters.put(RequestParameter.CAR_DESCRIPTION, "Audi a6 2,4i");
        parameters.put(RequestParameter.CAR_REGISTRATION_NUMBER, "1097AA");
        parameters.put(RequestParameter.CAR_CONDITIONER, "true");
        parameters.put(RequestParameter.CAR_TRANSMISSION, Car.TransmissionType.AUTOMATIC.name());
        parameters.put(RequestParameter.CAR_COST, "150");
        parameters.put(RequestParameter.CAR_STATUS, Car.CarStatus.FREE.name());
    }

    @Test(description = "Testing method add")
    public void add() throws ServiceException, DaoException {
        final var actual = service.addCar(parameters);
        Assert.assertTrue(actual);
    }


    @Test(description = "Testing method findById")
    public void findByIdTest() throws ServiceException {
        var actual = (service.findById(12)).get().getId();
        var expected = 12;
        Assert.assertEquals(actual, expected);
    }


    @Test(description = "Testing method updateStatus")
    public void updateStatusTest() throws ServiceException {
        boolean actual = false;
        final var car = service.updateStatus(12L, Car.CarStatus.BOOKED);
        var actualStatus = car.get().getCarStatus();
        var expectedStatus = Car.CarStatus.BOOKED;
        assertEquals(actualStatus,expectedStatus);
    }

    @AfterClass
    public void tierDown() {
        service = null;
    }
}