package by.karpov.rent_cars_final_project.command.impl.find;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.CarService;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FindCarByManufacturerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindCarByManufacturerCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        String manufacturer = request.getParameter(RequestParameter.CAR_MANUFACTURER);
        if (manufacturer != null && !manufacturer.isBlank()) {
            CarService carService = CarServiceImpl.getInstance();
            try {
                List<Car> cars = carService.findByManufacture(manufacturer);
                if (cars.isEmpty()) {
                    request.setAttribute(RequestParameter.LIST_IS_EMPTY, true);
                }
                request.setAttribute(RequestParameter.LIST_CARS, cars);
                router = new Router(PagePath.HOME_PAGE);
            } catch (ServiceException e) {
                LOGGER.error("error during find cars by manufacturer: ", e);
                router = new Router(PagePath.ERROR_500_PAGE);
            }
        } else {
            request.setAttribute(RequestParameter.CAR_MANUFACTURER_INCORRECT, true);
            return new Router(PagePath.HOME_PAGE);
        }
        return router;
    }
}
