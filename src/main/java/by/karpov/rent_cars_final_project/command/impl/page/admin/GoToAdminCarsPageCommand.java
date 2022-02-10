package by.karpov.rent_cars_final_project.command.impl.page.admin;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.*;

public class GoToAdminCarsPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToAdminAddCarPageCommand.class);
    private static final int LIMIT_ORDERS_ON_PAGE = 3;

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user == null || user.getRole() != User.UserRole.ADMIN) { //TODO
            return new Router(PagePath.ERROR_403_PAGE);
        }
        Router router;
        session.setAttribute(PREVIOUS_PAGE, PagePath.ADMIN_CARS_REDIRECT);
        String page = request.getParameter(RequestParameter.CURRENT_PAGE_NUMBER);
        int currentPageNumber;
        if (page != null) {
            currentPageNumber = Integer.parseInt(page);
        } else {
            currentPageNumber = 1;
        }
        CarServiceImpl carServiceImpl = CarServiceImpl.getInstance();
        try {
            double numberOfCars = carServiceImpl.countCars();
            double maxNumberOfPages = Math.ceil(numberOfCars / LIMIT_ORDERS_ON_PAGE);
            int leftBorderUsers = (LIMIT_ORDERS_ON_PAGE * (currentPageNumber - 1));
            List<Car> cars = carServiceImpl.findByLimit(leftBorderUsers, LIMIT_ORDERS_ON_PAGE);
            session.setAttribute(CURRENT_PAGE_NUMBER, currentPageNumber);
            session.setAttribute(MAX_NUMBER_OF_PAGES, maxNumberOfPages);
            session.setAttribute(RequestParameter.LIST_CARS, cars);
            router = new Router(PagePath.ADMIN_CARS_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("error on cars page: ", e);
            router = new Router(PagePath.ERROR_500_PAGE);
        }
        return router;
    }
}
