package by.karpov.rent_cars_final_project.command.impl.page.admin;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.CarService;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.karpov.rent_cars_final_project.command.RequestParameter.*;


public class AdminAddCarCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AdminAddCarCommand.class);

    public AdminAddCarCommand(CarService carService) {
        this.carService = carService;
    }

    private final CarService carService;

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        final var session = request.getSession();
        final var user = (User) session.getAttribute(SessionAttribute.USER);
        final var validator = InputDataValidator.getInstance();
        if (user == null || !validator.isActiveAdmin(user)) {
            return new Router(PagePath.ERROR_403_PAGE);
        }
        Map<String, String> parameters = new HashMap<>();
        final var cost = request.getParameter(CAR_COST);
        final var status = request.getParameter(CAR_STATUS);
        final var description = request.getParameter(CAR_DESCRIPTION);
        final var conditioner = request.getParameter(CAR_CONDITIONER);
        final var transmission = request.getParameter(CAR_TRANSMISSION);
        final var regNumber = request.getParameter(CAR_REGISTRATION_NUMBER);
        parameters.put(CAR_CONDITIONER, conditioner);
        parameters.put(CAR_TRANSMISSION, transmission);
        parameters.put(CAR_COST, cost);
        parameters.put(CAR_DESCRIPTION, description);
        parameters.put(CAR_REGISTRATION_NUMBER, regNumber);
        parameters.put(CAR_STATUS, status);
        if (checkInputDate(parameters, request)) {
            LOGGER.error("incorrect date of Car create");
            return new Router(PagePath.ADMIN_ADD_CAR_PAGE);
        } else {
            try {
                carService.addCar(parameters);
                router = new Router(PagePath.HOME_PAGE_REDIRECT);
                router.setRedirect();
            } catch (ServiceException e) {
                LOGGER.error("error on add car page: ", e);
                router = new Router(PagePath.ERROR_500_PAGE);
            }
            return router;
        }
    }

    private boolean checkInputDate(Map<String, String> parameters, HttpServletRequest request) {
        var count = 0;
        final var validator = InputDataValidator.getInstance();
        if (!validator.isStatusCarPresent(parameters.get(CAR_STATUS))) {
            request.setAttribute(INPUT_DATA_CAR_STATUS_INCORRECT, true);
            count++;
        }
        if (!validator.isTransmissionCarPresent(parameters.get(CAR_TRANSMISSION))) {
            request.setAttribute(INPUT_DATA_CAR_TRANSMISSION_INCORRECT, true);
            count++;
        }
        if (!validator.isCostValid(parameters.get(CAR_COST))) {
            request.setAttribute(INPUT_DATA_CAR_COST_INCORRECT, true);
            count++;
        }
        if (!validator.isDescriptionValid(parameters.get(CAR_DESCRIPTION))) {
            request.setAttribute(INPUT_DATA_CAR_DESCRIPTION_INCORRECT, true);
            count++;
        }
        if (!validator.isRegNumberValid(parameters.get(CAR_REGISTRATION_NUMBER))) {
            request.setAttribute(INPUT_DATA_CAR_REGISTRATION_NUMBER_INCORRECT, true);
            count++;
        }
        if (validator.isCarPresent(parameters.get(CAR_REGISTRATION_NUMBER))) {
            request.setAttribute(CAR_EXISTS, true);
            count++;
        }
        return count > 0;
    }
}
