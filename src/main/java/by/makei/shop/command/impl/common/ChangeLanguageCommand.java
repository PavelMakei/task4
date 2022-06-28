package by.makei.shop.command.impl.common;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import static by.makei.shop.command.PagePath.MAIN;

public class ChangeLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();

        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(AttributeName.LOCALE);
        String currentPage = (String) session.getAttribute(AttributeName.CURRENT_PAGE);
        String contextPath = session.getServletContext().getContextPath();
        logger.log(Level.INFO, "ChangeLocale currentLocale = {}, currentPage = {}, contextPath = {}", locale, currentPage, contextPath);
        String currentUri = contextPath + currentPage;
        if (locale.equals(AttributeName.LOCALE_RU_RU)) {
            session.setAttribute(AttributeName.LOCALE, AttributeName.LOCALE_EN_US);
        } else {
            session.setAttribute(AttributeName.LOCALE, AttributeName.LOCALE_RU_RU);
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
