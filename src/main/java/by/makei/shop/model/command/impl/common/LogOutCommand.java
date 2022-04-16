package by.makei.shop.model.command.impl.common;

import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.makei.shop.model.command.PagePath.INDEX;

public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.invalidate();
        router.setRedirectType();
        //TODO на каую страницу?
        //router.setCurrentPage(INDEX);
        return router;
    }
}

