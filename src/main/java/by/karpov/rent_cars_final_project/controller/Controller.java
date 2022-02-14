package by.karpov.rent_cars_final_project.controller;


import by.karpov.rent_cars_final_project.command.Command;
import by.karpov.rent_cars_final_project.command.CommandProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.karpov.rent_cars_final_project.command.PagePath.ERROR_404_PAGE;
import static by.karpov.rent_cars_final_project.command.RequestParameter.COMMAND;


@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private final CommandProvider provider = CommandProvider.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOGGER.info( "method processRequest()");
        final var commandName = request.getParameter(COMMAND);
        final var command = provider.getCommand(commandName);
        final var router = command.execute(request);
        switch (router.getType()) {
            case REDIRECT -> response.sendRedirect(router.getPagePath());
            case FORWARD -> request.getRequestDispatcher(router.getPagePath()).forward(request, response);
            default -> {
                LOGGER.error("unknown router type: {}", router.getType());
                response.sendRedirect(ERROR_404_PAGE);
            }
        }
    }
}
