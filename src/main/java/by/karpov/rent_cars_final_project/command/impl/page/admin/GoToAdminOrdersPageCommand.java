package by.karpov.rent_cars_final_project.command.impl.page.admin;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.impl.OrderServiceImpl;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.*;

public class GoToAdminOrdersPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToAdminUsersPageCommand.class);
    private static final int LIMIT_ORDERS_ON_PAGE = 3;

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        final var session = request.getSession();
        final var user = (User) session.getAttribute(USER);
        final var validator = InputDataValidator.getInstance();
        if (user == null || !validator.isAdmin(user)) {
            return new Router(PagePath.ERROR_403_PAGE);
        }
        Router router;
        session.setAttribute(PREVIOUS_PAGE, PagePath.ADMIN_ORDERS_REDIRECT);
        final var page = request.getParameter(RequestParameter.CURRENT_PAGE_NUMBER);
        int currentPageNumber;
        if (page != null) {
            currentPageNumber = Integer.parseInt(page);
        } else {
            currentPageNumber = 1;
        }
        final var orderService = OrderServiceImpl.getInstance();
        try {
            double numberOfOrders = orderService.countOrders();
            double maxNumberOfPages = Math.ceil(numberOfOrders / LIMIT_ORDERS_ON_PAGE);
            int leftBorderUsers = (LIMIT_ORDERS_ON_PAGE * (currentPageNumber - 1));
            List<Order> orders = orderService.findByLimit(leftBorderUsers, LIMIT_ORDERS_ON_PAGE);
            session.setAttribute(CURRENT_PAGE_NUMBER, currentPageNumber);
            session.setAttribute(MAX_NUMBER_OF_PAGES, maxNumberOfPages);
            session.setAttribute(RequestParameter.LIST_ORDERS, orders);
            router = new Router(PagePath.ADMIN_ORDERS_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("error on orders page: ", e);
            router = new Router(PagePath.ERROR_500_PAGE);
        }
        return router;
    }


}
