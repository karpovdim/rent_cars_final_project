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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.karpov.rent_cars_final_project.entity.User.UserStatus.ACTIVE;

public class CodeEntryCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CodeEntryCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        UserService userServiceImpl = UserServiceImpl.getInstance();
        String enteredCode = request.getParameter(RequestParameter.CODE);
        try {
            Optional<User> userOptional = userServiceImpl.findByPasswordForAuthentication(enteredCode);
            if (userOptional.isPresent()) {
                final var user = userOptional.get();
                user.setUserStatus(ACTIVE);
                userServiceImpl.update(user);
                userServiceImpl.updatePasswordForAuthentication(user.getId(), null);
                router = new Router(PagePath.HOME_PAGE_REDIRECT);
                router.setRedirect();
                LOGGER.info("the code is confirmed. Status changed to active");
            } else {
                LOGGER.error("the entered code is incorrect");
                request.setAttribute(RequestParameter.ENTERED_CODE_ERROR, true);
                router = new Router(PagePath.CODE_PAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("error while entering code", e);
            router = new Router(PagePath.ERROR_500_PAGE);
        }
        return router;
    }
}