package by.karpov.rent_cars_final_project.command.impl.find;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.command.impl.change.admin.ChangeUserRoleCommand;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.impl.OrderServiceImpl;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.stream.Collectors;

public class FindOrderByIdCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ChangeUserRoleCommand.class);


    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        final var session = request.getSession();
        final var user = (User) session.getAttribute(SessionAttribute.USER);
        final var validator = InputDataValidator.getInstance();
        if (!validator.isActiveAdmin(user)) {
            return new Router(PagePath.ERROR_403_PAGE);
        }
        final var id = request.getParameter(RequestParameter.ORDER_ID);
        if (id != null && !id.isBlank()) {
            try {
                OrderServiceImpl service = OrderServiceImpl.getInstance();
                long orderId = Long.parseLong(id);
                Optional<Order> order = service.findById(orderId);
                final var orders = order.stream().collect(Collectors.toList());
                request.setAttribute(RequestParameter.LIST_ORDERS, orders);
                router = new Router(PagePath.ADMIN_ORDERS_PAGE);
            } catch (ServiceException | NumberFormatException e) {
                LOGGER.error("error during find order by id: ", e);
                router = new Router(PagePath.ERROR_500_PAGE);
            }
        } else {
            request.setAttribute(RequestParameter.ORDER_ID_INCORRECT, true);
            return new Router(PagePath.ADMIN_ORDERS_PAGE);
        }
        return router;
    }
}
