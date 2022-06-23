package by.makei.shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

import static by.makei.shop.command.AttributeName.*;

@WebFilter(filterName = "ParameterCorrectFilter")
public class ParameterCorrectFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.log(Level.DEBUG, "ParameterCorrectFilter started");
        ArrayList<String> parametersToLowerCaseAndTrim = new ArrayList(List.of(LOGIN, EMAIL, PRODUCT_NAME));
        ArrayList<String> parametersToTrim = new ArrayList(List.of(PRODUCT_NAME, DESCRIPTION, SIZE, COLOUR));
        MutableHttpRequest mutableHttpRequest = new MutableHttpRequest((HttpServletRequest) request);
        for (String parameter: parametersToLowerCaseAndTrim) {
            String value= request.getParameter(parameter);
            if(value != null && !value.isEmpty()){
                mutableHttpRequest.addParameter(parameter, value.toLowerCase().trim());
            }
        }
        for (String parameter: parametersToTrim) {
            String value= request.getParameter(parameter);
            if(value != null && !value.isEmpty()){
                mutableHttpRequest.addParameter(parameter, value.trim());
            }
        }
        chain.doFilter(mutableHttpRequest, response);

    }

        @Override
        public void destroy() {	}


}
