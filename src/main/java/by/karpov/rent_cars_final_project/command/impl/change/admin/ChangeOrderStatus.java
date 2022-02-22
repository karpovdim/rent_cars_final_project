package by.karpov.rent_cars_final_project.command.impl.change.admin;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.OrderService;
import by.karpov.rent_cars_final_project.service.impl.OrderServiceImpl;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeOrderStatus implements Command {
	private static final Logger LOGGER = LogManager.getLogger(ChangeCarStatusCommand.class);
private final OrderService orderService;

	public ChangeOrderStatus(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public Router execute(HttpServletRequest request) {
		final var validator = InputDataValidator.getInstance();
		Router router;
		final var session = request.getSession();
		final var user = (User) session.getAttribute(SessionAttribute.USER);
		if (user == null || !validator.isActiveAdmin(user)) {
			return new Router(PagePath.ERROR_403_PAGE);
		}
		final var orderId = request.getParameter(RequestParameter.ORDER_ID);
		final var orderStatus = request.getParameter(RequestParameter.ORDER_STATUS);
		if (orderId != null && !orderId.isBlank() && validator.isStatusOrderPresent(orderStatus)) {
			try {
				final var optionalCar = orderService.updateStatus(Long.parseLong(orderId), Order.OrderStatus.valueOf(orderStatus));
				if (optionalCar.isPresent()) {
					router = new Router(PagePath.ADMIN_ORDERS_REDIRECT);
					router.setRedirect();
				} else {
					router = new Router(PagePath.ADMIN_ORDERS_PAGE);
					request.setAttribute(RequestParameter.ORDER_NOT_FOUND_OF_STATUS, true);
				}
			} catch (ServiceException | NumberFormatException e) {
				LOGGER.error("Exception when change status: {}", orderStatus, e);
				router = new Router(PagePath.ERROR_500_PAGE);
			}
		} else {
			router = new Router(PagePath.ADMIN_ORDERS_PAGE);
			request.setAttribute(RequestParameter.CHANGE_ORDER_STATUS_INCORRECT, true);
		}
		return router;
	}
}

