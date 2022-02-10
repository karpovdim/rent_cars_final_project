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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class ChangeCarCostCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(ChangeCarCostCommand.class);


	@Override
	public Router execute(HttpServletRequest request) {
		final var validator = InputDataValidator.getInstance();
		Router router;
		final var  session = request.getSession();
		final var user = (User) session.getAttribute(SessionAttribute.USER);
		if (user == null || !validator.isActiveAdmin(user)) {
			return new Router(PagePath.ERROR_403_PAGE);
		}
		final var carId = request.getParameter(RequestParameter.CAR_ID);
		final var carCost = request.getParameter(RequestParameter.CAR_COST);
		final var carService = CarServiceImpl.getInstance();
		if (carId != null && !carId.isBlank() && carCost != null && !carCost.isBlank()) {
			try {
				final var optionalCar = carService.updateCost(Long.parseLong(carId), new BigDecimal(carCost));
				if (optionalCar.isPresent()) {
					router = new Router(PagePath.ADMIN_CARS_REDIRECT);
					router.setRedirect();
				} else {
					router = new Router(PagePath.ADMIN_CARS_PAGE);
					request.setAttribute(RequestParameter.CAR_NOT_FOUND_OF_COST, true);
				}
			} catch (NumberFormatException | ServiceException e) {
				LOGGER.error("error during changing car cost: ", e);
				router = new Router(PagePath.ERROR_500_PAGE);
			}
		} else {
			router = new Router(PagePath.ADMIN_CARS_PAGE);
			request.setAttribute(RequestParameter.CHANGE_COST_INCORRECT, true);
		}
		return router;
	}
}
