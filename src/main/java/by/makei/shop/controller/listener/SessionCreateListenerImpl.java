package by.makei.shop.controller.listener;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionCreateListenerImpl implements  HttpSessionListener {
    static Logger logger = LogManager.getLogger();


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.log(Level.INFO, "------>>>session created :" + se.getSession().getId());
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.log(Level.INFO, "------session destroyed :" + se.getSession().getId());
        /* Session is destroyed. */
    }

}
