package by.karpov.rent_cars_final_project.command.impl.page.admin;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.UserService;
import by.karpov.rent_cars_final_project.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.*;

public class GoToAdminUsersPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToAdminUsersPageCommand.class);
    private static final int LIMIT_ORDERS_ON_PAGE = 3;

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user.getRole() != User.UserRole.ADMIN) {
            return new Router(PagePath.ERROR_403_PAGE);
        }
        Router router;
        session.setAttribute(PREVIOUS_PAGE, PagePath.ADMIN_USERS_REDIRECT);
        String page = request.getParameter(RequestParameter.CURRENT_PAGE_NUMBER);
        int currentPageNumber;
        if (page != null) {
            currentPageNumber = Integer.parseInt(page);
        } else {
            currentPageNumber = 1;
        }
        UserService userServiceImpl = UserServiceImpl.getInstance();
        try {
            double numberOfUsers = userServiceImpl.countUsers();
            double maxNumberOfPages = Math.ceil(numberOfUsers / LIMIT_ORDERS_ON_PAGE);
            int leftBorderUsers = (LIMIT_ORDERS_ON_PAGE * (currentPageNumber - 1));
            List<User> users = userServiceImpl.findByLimit(leftBorderUsers, LIMIT_ORDERS_ON_PAGE);
            session.setAttribute(CURRENT_PAGE_NUMBER, currentPageNumber);
            session.setAttribute(MAX_NUMBER_OF_PAGES, maxNumberOfPages);
            session.setAttribute(RequestParameter.LIST_USERS, users);
            router = new Router(PagePath.ADMIN_USERS_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("error on users page: ", e);
            router = new Router(PagePath.ERROR_500_PAGE);
        }
        return router;
    }
}
