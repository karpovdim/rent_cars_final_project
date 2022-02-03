package by.karpov.rent_cars_final_project.command.impl.change.admin;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.UserService;
import by.karpov.rent_cars_final_project.service.impl.UserServiceImpl;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);
    private final UserService userServiceImpl = UserServiceImpl.getInstance();
    private final InputDataValidator validator = InputDataValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        if (!validator.isActiveAdmin(user)) {
            return new Router(PagePath.ERROR_403_PAGE);
        }

        try {
            long userId = Long.parseLong(request.getParameter(RequestParameter.USER_ID));
            if (validator.isIdValid(userId) && !validator.isCanBeDelete(user, userId)) {
                request.setAttribute(RequestParameter.DELETE_USER_INCORRECT, true);
                return new Router(PagePath.ADMIN_USERS_PAGE);
            }
            final var optionalUser = userServiceImpl.findById(userId);
            if (optionalUser.isPresent()) {
                final var userDelete = optionalUser.get();
                userServiceImpl.delete(userDelete);
                router = new Router(PagePath.ADMIN_USERS_REDIRECT);
                router.setRedirect();
            } else {
                request.setAttribute(RequestParameter.USER_NOT_FOUND_OF_DELETE, true);
                return new Router(PagePath.ADMIN_USERS_PAGE);
            }
        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute(RequestParameter.DELETE_USER_INCORRECT, true);
            return new Router(PagePath.ADMIN_USERS_PAGE);
        }
        return router;
    }
}

