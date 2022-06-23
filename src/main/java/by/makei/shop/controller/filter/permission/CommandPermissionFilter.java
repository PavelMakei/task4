package by.makei.shop.controller.filter.permission;

import by.makei.shop.exception.CommandException;
import by.makei.shop.command.CommandType;
import by.makei.shop.model.entity.AccessLevel;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.EnumSet;

import static by.makei.shop.command.AttributeName.*;
import static by.makei.shop.command.PagePath.*;

@WebFilter(filterName = "CommandPermissionFilter")
public class CommandPermissionFilter implements Filter {
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
        String currentAccessLevel = (String) session.getAttribute(ACCESS_LEVEL);
        logger.log(Level.INFO, "CommandFilter access level = {}", currentAccessLevel);
        String command = httpServletRequest.getParameter(COMMAND);

        if (command == null || command.equals("")) {
            logger.log(Level.INFO, "CommandFilter command = {}", command);
            request.getRequestDispatcher(ERROR404).forward(httpServletRequest, httpServletResponse);
            return;
        }

        try {
            EnumSet<AccessLevel> accessLevels = CommandType.defineCommandAccessLevel(command);
            if (!accessLevels.contains(AccessLevel.valueOf(currentAccessLevel.toUpperCase()))) {
                logger.log(Level.ERROR, "accessLevel - {} is INCORRECT for command -{}", currentAccessLevel, command);
                request.setAttribute(MESSAGE, "Access is forbidden");
                request.getRequestDispatcher(ERROR403)
                        .forward(httpServletRequest, httpServletResponse);
                return;
            }
        } catch (CommandException e) {
            logger.log(Level.ERROR, "error while CommandType.defineCommandAccessLevel");
            request.getRequestDispatcher(ERROR404)
                    .forward(httpServletRequest, httpServletResponse);
            return;
        }
        logger.log(Level.INFO, "accessLevel - {} is correct for command -{}", currentAccessLevel, command);
        chain.doFilter(request, response);
    }
}
