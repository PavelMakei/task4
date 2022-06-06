package by.makei.shop.model.command.impl.common;

import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.makei.shop.model.command.PagePath.GO_TO_MAIN;
import static by.makei.shop.model.command.RedirectMessage.REDIRECT_MESSAGE;
import static by.makei.shop.model.command.RedirectMessage.USER_GOODBYE;

public class LogOutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.invalidate();
        router.setRedirectType();
        router.setCurrentPage(GO_TO_MAIN+REDIRECT_MESSAGE+USER_GOODBYE);
        return router;
    }
}

