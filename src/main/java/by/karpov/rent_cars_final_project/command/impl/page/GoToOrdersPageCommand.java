package by.karpov.rent_cars_final_project.command.impl.page;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.CarService;
import by.karpov.rent_cars_final_project.service.OrderService;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import by.karpov.rent_cars_final_project.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.*;

public class GoToOrdersPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToOrdersPageCommand.class);
    private static final int LIMIT_ORDERS_ON_PAGE = 3;
    private final OrderService orderService;

    public GoToOrdersPageCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        final var session = request.getSession();
        session.setAttribute(PREVIOUS_PAGE, PagePath.ORDERS_PAGE_REDIRECT);
        final var page = request.getParameter(RequestParameter.CURRENT_PAGE_NUMBER);
        int currentPageNumber;
        if (page != null) {
            currentPageNumber = Integer.parseInt(page);
        } else {
            currentPageNumber = 1;
        }
        final var leftBorderCars = (LIMIT_ORDERS_ON_PAGE * (currentPageNumber - 1));
        final var user = (User) session.getAttribute(USER);
        if (user == null ) {
            return new Router(PagePath.ERROR_403_PAGE);
        }
        try {
            final var numberOfOrders = orderService.countOrders(user.getId());
            final var maxNumberOfPages = Math.ceil(numberOfOrders / LIMIT_ORDERS_ON_PAGE);
            final var orders = orderService.findByUserIdAndLimit(user.getId(), leftBorderCars, LIMIT_ORDERS_ON_PAGE);
            session.setAttribute(CURRENT_PAGE_NUMBER, currentPageNumber);
            session.setAttribute(MAX_NUMBER_OF_PAGES, maxNumberOfPages);
            session.setAttribute(RequestParameter.LIST_ORDERS, orders);

            router = new Router(PagePath.ORDERS_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("error on orders page: ", e);
            router = new Router(PagePath.ERROR_500_PAGE);
        }
        return router;
    }
}
