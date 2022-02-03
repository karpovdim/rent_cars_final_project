package by.karpov.rent_cars_final_project.command.impl;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.UserService;
import by.karpov.rent_cars_final_project.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.*;
import static by.karpov.rent_cars_final_project.entity.User.UserStatus.ACTIVE;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class);
    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        HttpSession session = request.getSession();
        session.setAttribute(PREVIOUS_PAGE, PagePath.SIGN_IN_PAGE_REDIRECT);
        String email = request.getParameter(RequestParameter.USER_EMAIL);
        String password = request.getParameter(RequestParameter.USER_PASSWORD);
        try {
            Optional<User> userOptional = service.findByEmailAndPassword(email, password);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (ACTIVE == user.getUserStatus()) {
                    session.setAttribute(USER, user);
                    session.setAttribute(IS_AUTHENTICATED, true);
                    LOGGER.info("user signs in the system");
                }
                router = new Router(PagePath.HOME_PAGE_REDIRECT);
                router.setRedirect();
            } else {
                LOGGER.info("user is not found");
                request.setAttribute(RequestParameter.AUTHENTICATION_ERROR, true);
                router = new Router(PagePath.SIGN_IN_PAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("error during sign in user: ", e);
            router = new Router(PagePath.ERROR_500_PAGE);
        }
        return router;
    }
}
