package by.makei.shop.controller;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();



    public void init() {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String loginStr = request.getParameter("login");
        request.setAttribute("result", loginStr);

        try {
            request.getRequestDispatcher("pages/Main.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String strNum = request.getParameter("num");
        int resNum = 2 * Integer.parseInt(strNum);
        request.setAttribute("result", resNum);

        try {
            request.getRequestDispatcher("pages/Main.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }


    }

    public void destroy() {
    }
}