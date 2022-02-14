package by.karpov.rent_cars_final_project.command.impl.page;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.USER;

public class GoToChangeLastNamePageCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(GoToChangeLastNamePageCommand.class);

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.info("method execute()");
		final var session = request.getSession();
		final var user = (User) session.getAttribute(USER);
		if (user == null ) {
			return new Router(PagePath.ERROR_403_PAGE);
		}
		session.setAttribute(SessionAttribute.PREVIOUS_PAGE, PagePath.CHANGE_LAST_NAME_REDIRECT);
		return new Router(PagePath.CHANGE_LAST_NAME_PAGE);
	}
}
