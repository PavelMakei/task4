package by.makei.shop.controller;

import by.makei.shop.exception.CommandException;
import by.makei.shop.command.Command;
import by.makei.shop.command.CommandType;
import by.makei.shop.command.Router;
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

import static by.makei.shop.command.AttributeName.ERROR_MESSAGE;
import static by.makei.shop.command.PagePath.ERROR500;


/**
 * Controller class
 * Processes all requests after filtering.
 */

@WebServlet(name = "Controller", value = "/controller")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 25)


public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.DEBUG, "controller {}", request.getMethod());
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
            logger.log(Level.ERROR, "Command exception", e);
            request.setAttribute(ERROR_MESSAGE, "Command exception " + e);
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR500);
            dispatcher.forward(request, response);
        }
    }
}
