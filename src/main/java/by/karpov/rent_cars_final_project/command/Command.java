package by.karpov.rent_cars_final_project.command;

import by.karpov.rent_cars_final_project.controller.Router;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The Interface Command
 */
@FunctionalInterface
public interface Command {

	/**
	 * Execute data and make router.
	 *
	 * @param request the request
	 * @return the router
	 */
	Router execute(HttpServletRequest request);
}
