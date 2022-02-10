package by.karpov.rent_cars_final_project.command.impl.page;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.controller.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.IS_AUTHENTICATED;
import static by.karpov.rent_cars_final_project.command.SessionAttribute.USER;

public class SignOutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SignOutCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        Router router;
        HttpSession session = request.getSession();
        session.setAttribute(USER, null);
        session.setAttribute(IS_AUTHENTICATED, false);
        request.getSession().invalidate();
        router = new Router(PagePath.HOME_PAGE_REDIRECT);
        router.setRedirect();
        LOGGER.info("user signs out from the system");
        return router;
    }
}
