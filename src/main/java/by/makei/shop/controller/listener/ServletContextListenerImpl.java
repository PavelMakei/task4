package by.makei.shop.controller.listener;

import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.makei.shop.model.command.AttributeName.BRANDS_MAP;
import static by.makei.shop.model.command.AttributeName.TYPES_MAP;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.log(Level.DEBUG, "------context initialized :{}", sce.getServletContext().getServerInfo());
        // create connection pool or replace it to controller init
        DbConnectionPool.getInstance();
        //add maps to servlet context
        Map<String, String> brands;
        Map<String, String> types;
        ServletContext servletContext = sce.getServletContext();
        ProductService productService = ProductServiceImpl.getInstance();
        try {
            brands = productService.findAllBrandsMap();
            types = productService.findAllTypesMap();
            servletContext.setAttribute(BRANDS_MAP, brands);
            servletContext.setAttribute(TYPES_MAP, types);
//            throw new ServiceException("TEST REASON");
        } catch (ServiceException e) {
            //continue has no sense. Stop program
            logger.log(Level.ERROR, "ServletContextEvent error while context create. {}", e.getMessage());
            throw new RuntimeException(e);
        }
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.log(Level.DEBUG, "------context destroyed :{}",sce.getServletContext().getServerInfo());
        // shutdown connection pool or replace it to controller destroy
        DbConnectionPool.getInstance().shutdown();
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

}
