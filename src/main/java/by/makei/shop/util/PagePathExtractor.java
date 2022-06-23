package by.makei.shop.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.makei.shop.command.AttributeName.CURRENT_CONTEXT_PATH;
import static by.makei.shop.command.AttributeName.CURRENT_PAGE;

public class PagePathExtractor {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONTROLLER_PART = "/controller?";

    private PagePathExtractor(){}

    public static String extractAndSetToSessionPagePathAndContextPath(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        String commandPart = request.getQueryString();
        String currentPage = CONTROLLER_PART + commandPart;
        session.setAttribute(CURRENT_PAGE, currentPage);
        session.setAttribute(CURRENT_CONTEXT_PATH, contextPath);
        logger.log(Level.DEBUG,"current context path = '{}' and current page ='{}'",contextPath,currentPage);

        return currentPage;
    }
}
