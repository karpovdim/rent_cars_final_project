package by.karpov.rent_cars_final_project.command.impl.change;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.command.impl.change.admin.DeleteUserCommand;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.service.UserService;
import by.karpov.rent_cars_final_project.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.USER;

public class ChangePasswordCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(ChangePasswordCommand.class);
	private final UserService userService;

	public ChangePasswordCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.info("method execute()");
		Router router;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER);
		if (user == null ) {
			return new Router(PagePath.ERROR_403_PAGE);
		}
		String newPassword = request.getParameter(RequestParameter.NEW_PASSWORD);
		String oldPassword = request.getParameter(RequestParameter.OLD_PASSWORD);
		if (userService.updatePassword(user.getEmailLogin(), oldPassword, newPassword)) {
			router = new Router(PagePath.HOME_PAGE_REDIRECT);
			router.setRedirect();
			LOGGER.info("the password was changed successfully");
		} else {
			LOGGER.info("entered data is incorrect");
			router = new Router(PagePath.CHANGE_PASSWORD_PAGE);
			request.setAttribute(RequestParameter.CHANGE_ERROR, true);
		}
		return router;
	}
}
