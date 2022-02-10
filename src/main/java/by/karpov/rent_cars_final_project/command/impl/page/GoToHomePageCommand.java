package by.karpov.rent_cars_final_project.command.impl.page;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.*;


public class GoToHomePageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToHomePageCommand.class);
    private static final int LIMIT_CARS_ON_PAGE = 3;
    private final CarServiceImpl carServiceImpl = CarServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        HttpSession session = request.getSession();
        session.setAttribute(PREVIOUS_PAGE, PagePath.HOME_PAGE_REDIRECT);
        String page = request.getParameter(RequestParameter.CURRENT_PAGE_NUMBER);
        int currentPageNumber = page != null ? Integer.parseInt(page) : 1;
        int leftBorderCars = (LIMIT_CARS_ON_PAGE * (currentPageNumber - 1));
        try {
            double numberOfCars = carServiceImpl.countCars();
            double maxNumberOfPages = Math.ceil(numberOfCars / LIMIT_CARS_ON_PAGE);
            List<Car> cars = carServiceImpl.findByLimit(leftBorderCars, LIMIT_CARS_ON_PAGE);
            session.setAttribute(CURRENT_PAGE_NUMBER, currentPageNumber);
            session.setAttribute(MAX_NUMBER_OF_PAGES, maxNumberOfPages);
            session.setAttribute(RequestParameter.LIST_CARS, cars);
            router = new Router(PagePath.HOME_PAGE);
        } catch (Exception e) {
            LOGGER.error("error on home page: ", e);
            router = new Router(PagePath.ERROR_500_PAGE);
        }
        return router;
    }
}
