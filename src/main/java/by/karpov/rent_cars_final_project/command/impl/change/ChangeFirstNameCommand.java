package by.karpov.rent_cars_final_project.command.impl.change;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.RequestParameter;
import by.karpov.rent_cars_final_project.command.impl.change.admin.DeleteUserCommand;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.USER;

public class ChangeFirstNameCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);


	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.info( "method execute()");
		Router router;
		var session = request.getSession();
		final var service = UserServiceImpl.getInstance();
		var user = (User) session.getAttribute(USER);
		var name = request.getParameter(RequestParameter.USER_FIRST_NAME);
		final var id = user.getId();
		if (service.updateFirstName(id, name)) {
			user.setFirstName(name);
			session.setAttribute(USER, user);
			router = new Router(PagePath.HOME_PAGE_REDIRECT);
			router.setRedirect();
			LOGGER.info( "the name was changed successfully");
		} else {
			LOGGER.info( "entered data is incorrect");
			router = new Router(PagePath.CHANGE_FIRST_NAME_PAGE);
			request.setAttribute(RequestParameter.CHANGE_ERROR, true);
		}
		return router;
	}
}
