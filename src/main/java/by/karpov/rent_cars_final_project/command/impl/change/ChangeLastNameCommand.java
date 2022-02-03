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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeLastNameCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(ChangeLastNameCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.info("method execute()");
		Router router;
		HttpSession session = request.getSession();
		UserService service = UserServiceImpl.getInstance();
		User user = (User) session.getAttribute(SessionAttribute.USER);
		String name = request.getParameter(RequestParameter.USER_LAST_NAME);
		if (service.updateLastName(user.getId(), name)) {
			user.setLastName(name);
			session.setAttribute(SessionAttribute.USER, user);
			router = new Router(PagePath.HOME_PAGE_REDIRECT);
			router.setRedirect();
			LOGGER.info( "the name was changed successfully");
		} else {
			LOGGER.info("entered data is incorrect");
			router = new Router(PagePath.CHANGE_LAST_NAME_PAGE);
			request.setAttribute(RequestParameter.CHANGE_ERROR, true);
		}
		return router;
	}
}
