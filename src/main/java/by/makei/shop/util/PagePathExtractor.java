package by.makei.shop.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PagePathExtractor {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONTROLLER_PART = "/controller?";

    private PagePathExtractor(){}

    public static String extractPagePath(HttpServletRequest request) {
        String commandPart = request.getQueryString();
        String currentPage = CONTROLLER_PART + commandPart;
        return currentPage;
    }
}
