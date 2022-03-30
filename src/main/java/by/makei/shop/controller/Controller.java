package by.makei.shop.controller;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();



    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
request.getRequestDispatcher().forward(request,response);

    }

    public void destroy() {
    }
}