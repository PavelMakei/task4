package by.makei.shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

//@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/pages/*"})
public class PageRedirectSecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private String indexPath;
    public void init(FilterConfig config) throws ServletException {

        indexPath = config.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.log(Level.INFO, "Security filter started");
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
