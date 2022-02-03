package by.karpov.rent_cars_final_project.command.impl.change;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.service.UserService;
import by.karpov.rent_cars_final_project.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangePasswordCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		logger.log(Level.INFO, "method execute()");
		Router router;
		HttpSession session = request.getSession();
		UserService service = UserServiceImpl.getInstance();
		User user = (User) session.getAttribute(SessionAttribute.USER);
		String newPassword = request.getParameter(RequestParameter.NEW_PASSWORD);
		String oldPassword = request.getParameter(RequestParameter.OLD_PASSWORD);
		if (service.updatePassword(user.getEmailLogin(), oldPassword, newPassword)) {
			router = new Router(PagePath.HOME_PAGE_REDIRECT);
			router.setRedirect();
			logger.log(Level.INFO, "the password was changed successfully");
		} else {
			logger.log(Level.INFO, "entered data is incorrect");
			router = new Router(PagePath.CHANGE_PASSWORD_PAGE);
			request.setAttribute(RequestParameter.CHANGE_ERROR, true);
		}
		return router;
	}
}