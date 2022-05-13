package by.makei.shop.controller;

import by.makei.shop.exception.CommandException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.CommandType;
import by.makei.shop.model.command.Router;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


import static by.makei.shop.model.command.AttributeName.COMMAND;
import static by.makei.shop.model.command.AttributeName.ERROR_MESSAGE;


@WebServlet(name = "Controller", value = "/controller")
//@MultipartConfig(maxFileSize = 16777215)

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 25)

//порог для записи из памяти

public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.DEBUG, "controller " + request.getMethod());
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.DEBUG, "controller {}", request.getMethod());
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Command command = CommandType.defineCommand(request);
            Router router = command.execute(request);
            switch (router.getCurrentType()) {
                case FORWARD -> {
                    logger.log(Level.INFO, "forward type. To page :{}", router.getCurrentPage());
                    RequestDispatcher dispatcher = request.getRequestDispatcher(router.getCurrentPage());
                    dispatcher.forward(request, response);
                }
                case REDIRECT -> {
                    logger.log(Level.INFO, "redirect type. To page :{}", router.getCurrentPage());
                    response.sendRedirect(router.getCurrentPage());
                }
            }
        } catch (CommandException e) {
            logger.log(Level.ERROR, "incorrect command : {}", e.getMessage());
            request.setAttribute(ERROR_MESSAGE, "incorrect command.");
            response.sendError(500, e.getMessage());
        }
    }
}
