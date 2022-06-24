package by.makei.shop.controller.filter.permission;

import by.makei.shop.command.PagePath;
import by.makei.shop.model.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.makei.shop.command.AttributeName.*;
import static by.makei.shop.command.PagePath.ERROR403;
import static by.makei.shop.command.PagePath.ERROR404;

@WebFilter(filterName = "PagePermissionFilter")
public class PagePermissionFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String accessLevel = (String) session.getAttribute(ACCESS_LEVEL);
        User user = (User) session.getAttribute(USER);
        int userId = user == null ? 0 : user.getId();
        //куда идём?
        String requestURI = httpServletRequest.getRequestURI();

        boolean isAllowed = switch (accessLevel) {
            case "ADMIN" -> PagePath.adminPages.contains(requestURI);
            case "USER" -> PagePath.userPages.contains(requestURI);
            case "GUEST" -> PagePath.guestPages.contains(requestURI);
            case "BLOCKED" -> PagePath.blockedPages.contains(requestURI);
            default -> false;
        };
        if (!isAllowed) {
            logger.log(Level.INFO, "Access forbidden! userId: {}, accessLevel: {}, try to go to page {}", userId, accessLevel, requestURI);
            httpServletRequest.setAttribute(MESSAGE, "Page not found");
            request.getRequestDispatcher(ERROR404)
                    .forward(httpServletRequest, httpServletResponse);
            return;
        }
        logger.log(Level.DEBUG, "Access approved! userId: {}, accessLevel: {}, try to go to page {}", userId, accessLevel, requestURI);

        chain.doFilter(request, response);
    }
}
