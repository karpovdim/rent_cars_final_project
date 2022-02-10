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
import by.karpov.rent_cars_final_project.service.OrderPaymentService;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import by.karpov.rent_cars_final_project.service.impl.OrderPaymentServiceImpl;
import by.karpov.rent_cars_final_project.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.karpov.rent_cars_final_project.command.RequestParameter.NOT_ENOUGH_MONEY_TO_PAY;
import static by.karpov.rent_cars_final_project.command.SessionAttribute.USER;

public class PaymentCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(PaymentCommand.class);
    private static final String CARD_NUMBER = "card_number";
    private static final String CVV = "cvv";

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user == null ) {
            return new Router(PagePath.ERROR_403_PAGE);
        }
        String cardNumber = request.getParameter(CARD_NUMBER);
        String cvv = request.getParameter(CVV);
        long orderId = (long) session.getAttribute(SessionAttribute.ORDER_ID);
        Car car = (Car) session.getAttribute(SessionAttribute.CAR);
        OrderServiceImpl orderService = OrderServiceImpl.getInstance();
        OrderPaymentService paymentService = OrderPaymentServiceImpl.getInstance();
        try {
            Optional<Order> order = orderService.findById(orderId);
            if (order.isEmpty()) {
                LOGGER.error("order is not found");
                return new Router(PagePath.ERROR_500_PAGE);
            }
            if (paymentService.payForOrder(cardNumber, cvv, order.get().getPrice())) {
                orderService.updateStatus(order.get().getId(), Order.OrderStatus.PAID);
                LOGGER.info("Status changed to paid");
                router = new Router(PagePath.HOME_PAGE_REDIRECT);
                router.setRedirect();
            } else {
                LOGGER.error("not enough money to pay");
                orderService.updateStatus(orderId, Order.OrderStatus.DECLINED);
                CarService carService = CarServiceImpl.getInstance();
                carService.updateStatus(car.getId(), Car.CarStatus.FREE);
                router = new Router(PagePath.PAYMENT_PAGE);
                request.setAttribute(NOT_ENOUGH_MONEY_TO_PAY, true);
            }
        } catch (ServiceException e) {
            LOGGER.error("error while payment order", e);
            router = new Router(PagePath.ERROR_500_PAGE);
        }
        return router;
    }
}
