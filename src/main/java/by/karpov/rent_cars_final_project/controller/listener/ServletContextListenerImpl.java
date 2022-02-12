package by.karpov.rent_cars_final_project.controller.listener;

import by.karpov.rent_cars_final_project.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ConnectionPool.pool();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
			ConnectionPool.pool().closePool();
	}
}
