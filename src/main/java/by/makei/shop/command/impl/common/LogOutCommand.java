package by.makei.shop.command.impl.common;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.RedirectMessage;
import by.makei.shop.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.makei.shop.command.PagePath.GO_TO_MAIN;

public class LogOutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(AttributeName.LOCALE);
        session.invalidate();
        session = request.getSession(true);
        session.setAttribute(AttributeName.LOCALE, locale);
        router.setRedirectType();
        router.setCurrentPage(GO_TO_MAIN + RedirectMessage.REDIRECT_MESSAGE + RedirectMessage.USER_GOODBYE);
        return router;
    }
}

