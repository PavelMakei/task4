package by.makei.shop.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;

import static by.makei.shop.controller.Parameter.COMMAND;

@WebServlet(name = "Controller", value = "/controller")
//@MultipartConfig(maxFileSize = 16777215)

//@MultipartConfig(fileSizeThreshold = 1024 * 1024,
//        maxFileSize = 1024 * 1024 * 5,
//        maxRequestSize = 1024 * 1024 * 25)

public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

//TODO remove init?
    // public void init() {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /////////
        ServletContext servletContext = getServletContext();
        HttpSession session = request.getSession(true);
        session.setAttribute("attributeName","attributeValue");

        //////////
        logger.log(Level.DEBUG, "controller " + request.getMethod());
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.DEBUG, "controller " + request.getMethod());
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandName = request.getParameter(COMMAND);
        logger.log(Level.DEBUG, "controller get command : {}", commandName);


        //TODO
        if(commandName!=null && commandName.equals("login")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/pages/Login.jsp");
            dispatcher.forward(request, response);

           // response.sendRedirect("/view/Login.jsp");
        }else{
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user");
            dispatcher.forward(request, response);
          //  response.sendRedirect("/view/Main.jsp");
        }

    }

    // TODO Destroy add shutdown DBConnectionPool?

}
