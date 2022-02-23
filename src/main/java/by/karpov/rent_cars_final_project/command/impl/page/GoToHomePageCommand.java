package by.karpov.rent_cars_final_project.command.impl.page;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.service.CarService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.*;


public class GoToHomePageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToHomePageCommand.class);
    private static final int LIMIT_CARS_ON_PAGE = 3;
    private final CarService carService;

    public GoToHomePageCommand(CarService carService) {
        this.carService = carService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        final var session = request.getSession();
        session.setAttribute(PREVIOUS_PAGE, PagePath.HOME_PAGE_REDIRECT);
        final var page = request.getParameter(RequestParameter.CURRENT_PAGE_NUMBER);
        final var currentPageNumber = page != null ? Integer.parseInt(page) : 1;
        final var leftBorderCars = (LIMIT_CARS_ON_PAGE * (currentPageNumber - 1));
        try {
            final var numberOfCars = carService.countCars();
            final var maxNumberOfPages = Math.ceil(numberOfCars / LIMIT_CARS_ON_PAGE);
            final var cars = carService.findByLimit(leftBorderCars, LIMIT_CARS_ON_PAGE);
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
