package by.karpov.rent_cars_final_project.command.impl.change;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.SessionAttribute;
import by.karpov.rent_cars_final_project.controller.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeLanguageToEnglishCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(ChangeLanguageToEnglishCommand.class);
	private static final String ENGLISH = "en";

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.info("method execute()");
		HttpSession session = request.getSession();
		session.setAttribute(SessionAttribute.LOCALE, ENGLISH);
		Router router = new Router(session.getAttribute(SessionAttribute.PREVIOUS_PAGE).toString());
		router.setRedirect();
		LOGGER.info("language was changed to English");
		return router;
	}
}
