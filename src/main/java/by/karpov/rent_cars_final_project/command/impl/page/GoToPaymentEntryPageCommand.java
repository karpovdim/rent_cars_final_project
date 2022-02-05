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

public class GoToPaymentEntryPageCommand implements Command {
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public Router execute(HttpServletRequest request) {
		logger.log(Level.INFO, "method execute()");
		HttpSession session = request.getSession();
		session.setAttribute(SessionAttribute.PREVIOUS_PAGE, PagePath.PAYMENT_PAGE_REDIRECT);
		return new Router(PagePath.PAYMENT_PAGE);
	}
}
