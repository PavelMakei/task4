
package by.makei.shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


//@WebFilter(filterName = "ForwardFilter", urlPatterns = "/pages/*", dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.INCLUDE})
public class ForwardFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        logger.log(Level.INFO, "-----> Session in  PreIndexFilter" + (session != null ? session.getId() : "session not created"));
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}