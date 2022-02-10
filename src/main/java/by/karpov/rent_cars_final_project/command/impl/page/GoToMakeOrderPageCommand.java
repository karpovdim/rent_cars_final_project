package by.karpov.rent_cars_final_project.command.impl.page;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class GoToMakeOrderPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToMakeOrderPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        HttpSession session = request.getSession();
        CarServiceImpl service = CarServiceImpl.getInstance();
        String carId = request.getParameter(RequestParameter.CAR_ID);
        session.setAttribute(SessionAttribute.PREVIOUS_PAGE,
                PagePath.MAKE_ORDER_REDIRECT + "&" + RequestParameter.CAR_ID + "=" + carId);
        try {
            Optional<Car> optionalCar = service.findById(Long.parseLong(carId));
            if (optionalCar.isPresent()) {
                Car car = optionalCar.get();
                router = new Router(PagePath.MAKE_ORDER_PAGE);
                session.setAttribute(SessionAttribute.CAR, car);
            } else {
                LOGGER.error("car is not found");
                router = new Router(PagePath.ERROR_404_PAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("error on home page: ", e);
            router = new Router(PagePath.ERROR_500_PAGE);
        }
        return router;
    }
}