package by.karpov.rent_cars_final_project.command.impl.change.admin;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteCarCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
         final var validator = InputDataValidator.getInstance();
        if (!validator.isActiveAdmin(user)) {
            return new Router(PagePath.ERROR_403_PAGE);
        }
        try {
            long carId = Long.parseLong(request.getParameter(RequestParameter.CAR_ID));
            if (!validator.isIdValid(carId) ) {
                request.setAttribute(RequestParameter.DELETE_CAR_INCORRECT, true);
                return new Router(PagePath.ADMIN_CARS_PAGE);
            }
            final var carService = CarServiceImpl.getInstance();
            final var optionalCar = carService.findById(carId);
            if (optionalCar.isPresent()) {
                final var carDelete = optionalCar.get();
                carService.delete(carDelete);
                router = new Router(PagePath.ADMIN_CARS_REDIRECT);
                router.setRedirect();
            } else {
                request.setAttribute(RequestParameter.CAR_NOT_FOUND_OF_DELETE, true);
                return new Router(PagePath.ADMIN_CARS_PAGE);
            }
        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute(RequestParameter.DELETE_CAR_INCORRECT, true);
            return new Router(PagePath.ADMIN_CARS_PAGE);
        }
        return router;
    }
}


