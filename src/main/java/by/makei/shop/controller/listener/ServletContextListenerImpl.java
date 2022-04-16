package by.makei.shop.controller.listener;

import by.makei.shop.model.connectionpool.DbConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.log(Level.DEBUG, "------context initialized :" + sce.getServletContext().getServerInfo());
        // create connection pool or replace it to controller init
        DbConnectionPool.getInstance();

        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.log(Level.DEBUG, "------context destroyed :" + sce.getServletContext().getServerInfo());
        // shutdown connection pool or replace it to controller destroy
        DbConnectionPool.getInstance().shutdown();

        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

}
