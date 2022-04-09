package by.makei.shop.controller.listener;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

@WebListener
public class SessionCreateListenerImpl implements  HttpSessionListener {
    static Logger logger = LogManager.getLogger();
    //private static final String DEFAULT_LOCALE = "en_US";
    private static final String DEFAULT_LOCALE = "ru_RU";


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        session.setAttribute("locale", DEFAULT_LOCALE);

        logger.log(Level.INFO, "------>>>session created :" + session.getId());

        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.log(Level.INFO, "------session destroyed :" + se.getSession().getId());
        /* Session is destroyed. */
    }

}
