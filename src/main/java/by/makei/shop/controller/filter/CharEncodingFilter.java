package by.makei.shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter
public class CharEncodingFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String DEFAULT_CONTEXT_TYPE = "text/html; charset=UTF-8";

    public void init() throws ServletException {
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {
        logger.log(Level.DEBUG, "CharEncodingFilter started");
        request.setCharacterEncoding(DEFAULT_ENCODING);
        response.setContentType(DEFAULT_CONTEXT_TYPE);
        response.setCharacterEncoding(DEFAULT_ENCODING);
        next.doFilter(request, response);
        logger.log(Level.DEBUG, "CharEncodingFilter on out");
    }
}