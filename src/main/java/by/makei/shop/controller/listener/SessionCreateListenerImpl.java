package by.makei.shop.controller.listener;

import by.makei.shop.model.entity.Cart;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.makei.shop.command.AttributeName.*;
import static by.makei.shop.command.PagePath.INDEX;
import static by.makei.shop.model.entity.AccessLevel.GUEST;

@WebListener
public class SessionCreateListenerImpl implements HttpSessionListener {
    static Logger logger = LogManager.getLogger();
    //    private static final String DEFAULT_LOCALE = "en_US";
    private static final String DEFAULT_LOCALE = "ru_RU";
    private static final String DEFAULT_ACCESS_LEVEL = GUEST.toString();


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(CURRENT_PAGE, INDEX);
        session.setAttribute(SESS_CART, new Cart());
        session.setAttribute(ACCESS_LEVEL, DEFAULT_ACCESS_LEVEL);

        logger.log(Level.INFO, "session created :{}", session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.log(Level.INFO, "session destroyed :{}", se.getSession().getId());
    }

}
