package by.karpov.rent_cars_final_project.command.impl.page;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoToPersonalProfilePageCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(GoToPersonalProfilePageCommand.class);


	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.info( "method execute()");
		Router router;
		var	 session = request.getSession();
		session.setAttribute(SessionAttribute.PREVIOUS_PAGE, PagePath.PERSONAL_PROFILE_REDIRECT);
		router = new Router(PagePath.PERSONAL_PROFILE_PAGE);
		return router;
	}
}
