package by.karpov.rent_cars_final_project.command.impl.page.admin;

import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.PagePath;
import by.karpov.rent_cars_final_project.controller.Router;
import by.karpov.rent_cars_final_project.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.PREVIOUS_PAGE;
import static by.karpov.rent_cars_final_project.command.SessionAttribute.USER;

public class GoToAdminAddCarPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToAdminAddCarPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("method execute()");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user == null || user.getRole() != User.UserRole.ADMIN) {
            return new Router(PagePath.ERROR_403_PAGE);
        }
        session.setAttribute(PREVIOUS_PAGE, PagePath.ADMIN_ADD_CAR_REDIRECT);
        return new Router(PagePath.ADMIN_ADD_CAR_PAGE);
    }
}
