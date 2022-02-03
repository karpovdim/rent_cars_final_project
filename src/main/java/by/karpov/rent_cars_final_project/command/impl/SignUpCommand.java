package by.karpov.rent_cars_final_project.command.impl;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.UserService;
import by.karpov.rent_cars_final_project.service.impl.UserServiceImpl;
import by.karpov.rent_cars_final_project.util.CodeGenerator;
import by.karpov.rent_cars_final_project.util.EmailSender;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.karpov.rent_cars_final_project.command.RequestParameter.*;

public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);
    private final EmailSender emailSender = EmailSender.getInstance();
    private final UserService userServiceImpl = UserServiceImpl.getInstance();
    private final CodeGenerator codeGenerator = CodeGenerator.getInstance();
    private final String hashPassword = codeGenerator.generateCodeToRegistration();


    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Map<String, String> parameters = new HashMap<>();
        Router router;
        String dateOfBirth = request.getParameter(USER_DATE_OF_BIRTH);
        if (dateOfBirth != null && !dateOfBirth.isBlank()) {
            parameters.put(USER_EMAIL, request.getParameter(USER_EMAIL));
            parameters.put(USER_PASSWORD, request.getParameter(USER_PASSWORD));
            parameters.put(USER_FIRST_NAME, request.getParameter(USER_FIRST_NAME));
            parameters.put(USER_LAST_NAME, request.getParameter(USER_LAST_NAME));
            parameters.put(USER_PHONE_NUMBER, request.getParameter(USER_PHONE_NUMBER));
            parameters.put(USER_PASSWORD_FOR_AUTHENTICATION, hashPassword);
            try {
                Optional<User> user = userServiceImpl.findByEmail(parameters.get(USER_EMAIL));
                if (user.isEmpty()) {
                    if (userServiceImpl.registerUser(parameters)) {
                        emailSender.sendMail(parameters.get(USER_EMAIL), hashPassword);
                        router = new Router(PagePath.HOME_PAGE_REDIRECT);
                        router.setRedirect();
                        LOGGER.info("user was registered but do not enter a code");
                    } else {
                        LOGGER.error("error during registration user");
                        router = new Router(PagePath.ERROR_500_PAGE);
                    }
                } else {
                    LOGGER.info("user with this email was registered");
                    router = new Router(PagePath.SIGN_IN_PAGE);
                }
            } catch (ServiceException | com.google.protobuf.ServiceException e) {
                LOGGER.error("error during registration user: ", e);
                router = new Router(PagePath.ERROR_500_PAGE);
            }
        } else {
            LOGGER.error("user entered empty date");
            router = new Router(PagePath.SIGN_UP_PAGE);
        }
        return router;
    }
}