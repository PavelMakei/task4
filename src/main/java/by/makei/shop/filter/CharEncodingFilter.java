package by.makei.shop.filter;

import jakarta.servlet.*;
import java.io.IOException;

public class CharEncodingFilter implements Filter {

//    private String charSet ;

    public void init(FilterConfig parm) throws ServletException {
//        charSet = parm1.getInitParameter("charset") ;
//        if (charSet == null && charSet.length() < 1)
//        {
//            charSet = "UTF-8" ;
//        }
//        System.out.println("charencodingfilter — character set:" + this.getCharSet());
    }

    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }
}