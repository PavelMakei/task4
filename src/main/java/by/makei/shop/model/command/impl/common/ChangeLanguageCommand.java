package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;


import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.MAIN;

public class ChangeLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();

        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE);
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String contextPath = session.getServletContext().getContextPath();
        logger.log(Level.INFO, "ChangeLocaleCommand currentLocale = {}, currentPage = {}, contextPath = {}",locale,currentPage,contextPath);
        String currentUri = contextPath+currentPage;
        if (locale.equals(LOCALE_RU_RU)) {
            session.setAttribute(LOCALE, LOCALE_EN_US);
        } else {
            session.setAttribute(LOCALE, LOCALE_RU_RU);
        }

        if (currentPage != null) {
            router.setCurrentPage(currentUri);
            router.setRedirectType();
        } else {
            router.setCurrentPage(MAIN);
        }
        return router;

    }
}
