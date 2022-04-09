package by.makei.shop.controller.listener;

import by.makei.shop.model.connectionpool.DbConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@WebListener
public class ServletContextListenerImpl implements ServletContextListener{
    static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.log(Level.INFO, "------context initialized :" + sce.getServletContext().getServerInfo());
        // create connection pool
        DbConnectionPool.getInstance();

        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.log(Level.INFO, "------context destroyed :" + sce.getServletContext().getServerInfo());
        // shutdown connection pool
        DbConnectionPool.getInstance().shutdown();

        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

}
