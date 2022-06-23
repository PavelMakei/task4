package by.makei.shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Enumeration;

import static by.makei.shop.command.AttributeName.SESS_MESSAGE;

@WebFilter(filterName = "SessionAttributeClearFilter")
public class SessionAttributeClearFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Enumeration<String> attributes = session.getAttributeNames();
        while (attributes.hasMoreElements()){
            if(attributes.nextElement().equals(SESS_MESSAGE)){
                logger.log(Level.INFO,"SessionAttributeClearFilter message '{}' was removed", session.getAttribute(SESS_MESSAGE));
                session.removeAttribute(SESS_MESSAGE);
            }
        }
        logger.log(Level.INFO,"SessionAttributeClearFilter before chain");

        chain.doFilter(request, response);
        logger.log(Level.INFO,"SessionAttributeClearFilter after chain");

    }
}
