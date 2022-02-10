package by.karpov.rent_cars_final_project.command.impl.change;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.UserService;
import by.karpov.rent_cars_final_project.service.impl.UserServiceImpl;
import by.karpov.rent_cars_final_project.util.CodeGenerator;
import by.karpov.rent_cars_final_project.util.EmailSender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.USER;

public class ChangeEmailCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(ChangeEmailCommand.class);


	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.info( "method execute()");
		Router router;
		HttpSession session = request.getSession();
		UserService service = UserServiceImpl.getInstance();
		User user = (User) session.getAttribute(USER);
		if (user == null ) {
			return new Router(PagePath.ERROR_403_PAGE);
		}
		String email = request.getParameter(RequestParameter.USER_EMAIL);
		String password = request.getParameter(RequestParameter.USER_PASSWORD);
		final var newEmail = request.getParameter(RequestParameter.NEW_USER_EMAIL);
		try {
			if (service.updateEmail(user.getId(), email,newEmail, password)) {
				CodeGenerator codeGenerator = CodeGenerator.getInstance();
				String code = codeGenerator.generateCodeToRegistration();
				service.updateStatus(user.getId(), User.UserStatus.CONFIRMATION_AWAITING);
				service.updatePasswordForAuthentication(user.getId(), code);
				user.setEmailLogin(newEmail);
				session.setAttribute(SessionAttribute.IS_AUTHENTICATED, false);
				EmailSender emailSender = EmailSender.getInstance();
				emailSender.sendMail(newEmail, code);
				router = new Router(PagePath.HOME_PAGE_REDIRECT);
				router.setRedirect();
				LOGGER.info("the email was changed successfully");
			} else {
				LOGGER.info("entered data is incorrect");
				router = new Router(PagePath.CHANGE_EMAIL_PAGE);
				request.setAttribute(RequestParameter.CHANGE_ERROR, true);
			}
		} catch (ServiceException | com.google.protobuf.ServiceException e) {
			LOGGER.error("error during changing user email: ", e);
			router = new Router(PagePath.ERROR_500_PAGE);
		}
		return router;
	}
}
