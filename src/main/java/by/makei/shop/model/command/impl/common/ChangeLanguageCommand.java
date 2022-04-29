package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.makei.shop.model.command.AttributeName.*;

public class ChangeLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();

        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE);
//        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String previousRequest = request.getQueryString();
        String currentPage = "controller?command=go_to_add_new_product";
        System.out.println(previousRequest);

        if (locale.equals(LOCALE_RU_RU)) {
            session.setAttribute(LOCALE, LOCALE_EN_US);
        } else {
            session.setAttribute(LOCALE, LOCALE_RU_RU);
        }
        if (currentPage != null) {
            //TODO validate page?
            router.setCurrentPage(currentPage);
        }
//        router.setRedirectType();
        return router;
    }
}
