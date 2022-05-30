package by.makei.shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.makei.shop.model.command.AttributeName.CURRENT_PAGE;

//@WebFilter(filterName = "CurrentPageSetterFilter")
public class CurrentPageSetterFilter implements Filter {
        private static final Logger logger = LogManager.getLogger();
        private static final String CONTROLLER_PART = "/controller?";

        @Override
        public void init(FilterConfig config) throws ServletException {
            logger.log(Level.INFO,"CurrentPageSetterFilter init");
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpSession session = servletRequest.getSession();
            String requestURI = servletRequest.getRequestURI();
            logger.log(Level.INFO,"CurrentPageFilter request URI: {}", requestURI);
            String queryPart = servletRequest.getQueryString();
            if(queryPart != null){
                requestURI = CONTROLLER_PART + queryPart;
            }
            logger.log(Level.INFO, "CurrentPageFilter query: {}",queryPart);
            session.setAttribute(CURRENT_PAGE, requestURI);
            chain.doFilter(request, response);
        }
        public void destroy() {
        }
    }