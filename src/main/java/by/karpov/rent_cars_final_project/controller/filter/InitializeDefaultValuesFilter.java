package by.karpov.rent_cars_final_project.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.karpov.rent_cars_final_project.command.SessionAttribute.IS_AUTHENTICATED;
import static by.karpov.rent_cars_final_project.command.SessionAttribute.LOCALE;


@WebFilter(urlPatterns = { "/*" }, initParams = {
		@WebInitParam(name = "locale", value = "en", description = "Default language"),
		@WebInitParam(name = "isAuthenticated", value = "false", description = "User is not authenticated") })
public class InitializeDefaultValuesFilter implements Filter {
	private static final String ENGLISH = "en";
	String locale;
	boolean isAuthenticated;

	public void init(FilterConfig fConfig) {
		locale = fConfig.getInitParameter(LOCALE);
		isAuthenticated = Boolean.parseBoolean(fConfig.getInitParameter(IS_AUTHENTICATED));
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		if (session.getAttribute(LOCALE) == null) {
			session.setAttribute(LOCALE, ENGLISH);
		}
		if (session.getAttribute(IS_AUTHENTICATED) == null) {
			session.setAttribute(IS_AUTHENTICATED, false);
		}
		chain.doFilter(request, response);

	}

	public void destroy() {
		locale = null;
		isAuthenticated = false;
	}
}
