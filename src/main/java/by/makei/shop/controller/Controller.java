package by.makei.shop.controller;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.CommandFabric;
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
import java.util.Optional;

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
        try {
            processRequest(request, response);
        } catch (DaoException e) {
            //TODO what should I do with exception?
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.DEBUG, "controller " + request.getMethod());
        try {
            processRequest(request, response);
        } catch (DaoException e) {
            //TODO what should I do with exception?
            e.printStackTrace();
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DaoException {
        String commandName = request.getParameter(COMMAND);
        logger.log(Level.DEBUG, "controller get command : {}", commandName);
        Command command = CommandFabric.defineCommand(commandName);
        String page = command.execute(request);
//TODO проверить что происходит если неверна команда
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);

          //  response.sendRedirect("/view/main.jsp");


    }

    // TODO Destroy add shutdown DBConnectionPool?

}
