package by.makei.shop.controller.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.makei.shop.model.command.AttributeName.LOCALE;

@WebListener
public class SessionAttributeListenerImpl implements  HttpSessionAttributeListener {
    static Logger logger = LogManager.getLogger();

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        logger.log(Level.INFO, "------attribute added :" + sbe.getSession().getAttribute(LOCALE));
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        logger.log(Level.INFO, "------attribute removed :" + sbe.getSession().getAttribute(LOCALE));

        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        logger.log(Level.INFO, "------attribute replaced :" + sbe.getSession().getAttribute(LOCALE));
        /* This method is called when an attribute is replaced in a session. */
    }
}
