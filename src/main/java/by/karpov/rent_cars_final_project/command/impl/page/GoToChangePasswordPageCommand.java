package by.karpov.rent_cars_final_project.command.impl.page;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoToChangePasswordPageCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(GoToChangePasswordPageCommand.class);


	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.info("method execute()");
		HttpSession session = request.getSession();
		session.setAttribute(SessionAttribute.PREVIOUS_PAGE, PagePath.CHANGE_PASSWORD_REDIRECT);
		return new Router(PagePath.CHANGE_PASSWORD_PAGE);
	}
}
