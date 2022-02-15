package by.karpov.rent_cars_final_project.command.impl;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.CarService;
import by.karpov.rent_cars_final_project.service.OrderService;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import by.karpov.rent_cars_final_project.service.impl.OrderServiceImpl;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.karpov.rent_cars_final_project.command.RequestParameter.*;
import static by.karpov.rent_cars_final_project.command.SessionAttribute.USER;

public class MakeOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CodeEntryCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Map<String, String> parameters = new HashMap<>();
        Router router;
        final var session = request.getSession();
        final var user = (User) session.getAttribute(USER);
        if (user == null) {
            return new Router(PagePath.ERROR_403_PAGE);
        }
        final var car = (Car) session.getAttribute(SessionAttribute.CAR);

        final var rentDateParameters = request.getParameter(RENT_DATE);
        final var returnDateParameters = request.getParameter(RETURN_DATE);
        if (rentDateParameters == null || rentDateParameters.isBlank() || returnDateParameters == null
                || returnDateParameters.isBlank()) {
            LOGGER.info("user entered wrong dates");
            request.setAttribute(ORDER_INCORRECT_DATE, true);
            return new Router(PagePath.MAKE_ORDER_PAGE);
        }
        final var rentDate = LocalDate.parse(rentDateParameters);
        final var returnDate = LocalDate.parse(returnDateParameters);
        if (rentDate.isAfter(returnDate) || rentDate.isBefore(LocalDate.now())) {
            LOGGER.info("user entered the pick up date of the lease after the return date of the lease");
            request.setAttribute(ORDER_PICK_UP_BEFORE_RETURN, true);
            return new Router(PagePath.MAKE_ORDER_PAGE);
        }
        parameters.put(USER_ID, Long.toString(user.getId()));
        parameters.put(CAR_ID, Long.toString(car.getId()));
        parameters.put(RENT_DATE, rentDateParameters);
        parameters.put(RETURN_DATE, returnDateParameters);
        parameters.put(CAR_COST, car.getCost().toString());
        OrderService orderService = OrderServiceImpl.getInstance();
        CarService carService = CarServiceImpl.getInstance();
        try {
            final var optionalCar = carService.findById(car.getId());
            long orderId;
            if (optionalCar.isPresent() && optionalCar.get().getCarStatus() == Car.CarStatus.FREE) {
                orderId = orderService.add(parameters);
                session.setAttribute(SessionAttribute.ORDER_ID, orderId);
                router = new Router(PagePath.PAYMENT_PAGE_REDIRECT);
                router.setRedirect();
            } else if (optionalCar.isPresent() && optionalCar.get().getCarStatus() == Car.CarStatus.BOOKED) {
                final var listOrders = orderService.findByCarId(car.getId());
                final var validator = InputDataValidator.getInstance();
                if (!validator.isCarFreeOnThisDate(rentDate, returnDate, listOrders)) {
                    LOGGER.info("car booked on this date");
                    request.setAttribute(CAR_BOOKED, true);
                    return new Router(PagePath.MAKE_ORDER_PAGE);
                }
                orderId = orderService.add(parameters);
                session.setAttribute(SessionAttribute.ORDER_ID, orderId);
                carService.updateStatus(car.getId(), Car.CarStatus.BOOKED);
                router = new Router(PagePath.PAYMENT_PAGE_REDIRECT);
                router.setRedirect();
            } else {
                LOGGER.info("car booked on this date");
                request.setAttribute(CAR_BOOKED, true);
                router = new Router(PagePath.MAKE_ORDER_PAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("error during making order: ", e);
            router = new Router(PagePath.ERROR_500_PAGE);
        }
        return router;
    }


}
