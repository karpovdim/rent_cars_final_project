package by.karpov.rent_cars_final_project.command.impl.change.admin;


import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.impl.UserServiceImpl;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeUserStatusCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ChangeUserStatusCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        final var validator = InputDataValidator.getInstance();
        Router router;
        final var session = request.getSession();
        final var user = (User) session.getAttribute(SessionAttribute.USER);
        if (user == null || !validator.isActiveAdmin(user)) {
            return new Router(PagePath.ERROR_403_PAGE);
        }
        final var userService = UserServiceImpl.getInstance();
        final var userId = request.getParameter(RequestParameter.USER_ID);
        final var userStatus = request.getParameter(RequestParameter.USER_STATUS);
        if (userId != null && !userId.isBlank() && validator.isStatusUserPresent(userStatus)) {
            try {
                final var optionalUser = userService.updateStatus(Long.parseLong(userId), User.UserStatus.valueOf(userStatus));
                if (optionalUser.isPresent()) {
                    router = new Router(PagePath.ADMIN_USERS_REDIRECT);
                    router.setRedirect();
                } else {
                    router = new Router(PagePath.ADMIN_USERS_PAGE);
                    request.setAttribute(RequestParameter.USER_NOT_FOUND_OF_STATUS, true);
                }
            } catch (ServiceException | NumberFormatException e) {
                LOGGER.error("Exception when change status: {}", userStatus, e);
                router = new Router(PagePath.ERROR_500_PAGE);
            }
        } else {
            router = new Router(PagePath.ADMIN_USERS_PAGE);
            request.setAttribute(RequestParameter.CHANGE_STATUS_INCORRECT, true);
        }
        return router;
    }
}
