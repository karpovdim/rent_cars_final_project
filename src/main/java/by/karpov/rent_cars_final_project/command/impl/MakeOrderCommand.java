package by.karpov.rent_cars_final_project.command.impl;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.CarService;
import by.karpov.rent_cars_final_project.service.OrderService;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import by.karpov.rent_cars_final_project.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.karpov.rent_cars_final_project.command.RequestParameter.*;

public class MakeOrderCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		logger.log(Level.INFO, "method execute()");
		Map<String, String> parameters = new HashMap<>();
		Router router;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionAttribute.USER);
		Car car = (Car) session.getAttribute(SessionAttribute.CAR);
		
		String rentDateParameters = request.getParameter(RENT_DATE);
		String returnDateParameters = request.getParameter(RETURN_DATE);
		if (rentDateParameters == null || rentDateParameters.isBlank() || returnDateParameters == null
				|| returnDateParameters.isBlank()) { // todo validation in method
			logger.log(Level.INFO, "user entered wrong dates");
			request.setAttribute(ORDER_INCORRECT_DATE, true);
			return new Router(PagePath.MAKE_ORDER_PAGE);
		}
		LocalDate rentDate = LocalDate.parse(rentDateParameters);
		LocalDate returnDate = LocalDate.parse(returnDateParameters);
		if (rentDate.isAfter(returnDate) || rentDate.isBefore(LocalDate.now())) { // todo check in validator
			logger.log(Level.INFO, "user entered the pick up date of the lease after the return date of the lease");
			request.setAttribute(ORDER_PICK_UP_BEFORE_RETURN, true);
			return new Router(PagePath.MAKE_ORDER_PAGE);
		}

		parameters.put(USER_ID, Long.toString(user.getId()));
		parameters.put(CAR_ID, Long.toString(car.getId()));
		parameters.put(RENT_DATE, rentDateParameters);
		parameters.put(RETURN_DATE, returnDateParameters);
		parameters.put(CAR_COST, car.getCost().toString());
		//parameters.put(USER_DISCOUNT, Integer.toString(user.getDiscount()));
		//parameters.put(CAR_DISCOUNT, Integer.toString(car.getDiscount()));

		OrderService orderService = OrderServiceImpl.getInstance();
		CarService carService = CarServiceImpl.getInstance();
		try {
			Optional<Car> optionalCar = carService.findById(car.getId());
			long orderId;
			if (optionalCar.isPresent() && optionalCar.get().getCarStatus() == Car.CarStatus.FREE) {
				orderId = orderService.add(parameters);
				session.setAttribute(SessionAttribute.ORDER_ID, orderId);
				//carService.updateStatus(car.getId(), Car.CarStatus.BOOKED);
				router = new Router(PagePath.PAYMENT_PAGE_REDIRECT);
				router.setRedirect();
			} else if (optionalCar.isPresent() && optionalCar.get().getCarStatus() == Car.CarStatus.BOOKED) {
				List<Order> listOrders = orderService.findByCarId(car.getId());
				if (!isCarFreeOnThisDate(rentDate, returnDate, listOrders)) {
					logger.log(Level.INFO, "car booked on this date");
					request.setAttribute(CAR_BOOKED, true);
					return new Router(PagePath.MAKE_ORDER_PAGE);
				}
				orderId = orderService.add(parameters);
				session.setAttribute(SessionAttribute.ORDER_ID, orderId);
				carService.updateStatus(car.getId(), Car.CarStatus.BOOKED);
				router = new Router(PagePath.PAYMENT_PAGE_REDIRECT);
				router.setRedirect();
			} else {
				logger.log(Level.INFO, "car booked on this date");
				request.setAttribute(CAR_BOOKED, true);
				router = new Router(PagePath.MAKE_ORDER_PAGE);
			}
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "error during making order: ", e);
			router = new Router(PagePath.ERROR_500_PAGE);
		}
		return router;
	}

	private boolean isCarFreeOnThisDate(LocalDate rentDate, LocalDate returnDate, List<Order> listOrders) {
		int countTrueOptions = 0;
		for (Order order : listOrders) {
			if (returnDate.isBefore(order.getRentDate()) || rentDate.isAfter(order.getReturnDate())) {
				countTrueOptions++;
			}
		}
		return countTrueOptions == listOrders.size();
	}
}
