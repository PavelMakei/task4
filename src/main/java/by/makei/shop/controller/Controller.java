package by.makei.shop.controller;

import by.makei.shop.exception.DaoException;
import by.makei.shop.model.dao.impl.UserDaoImpl;
import by.makei.shop.model.entity.Builder.UserBuilder;
import by.makei.shop.model.entity.User;
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
import java.sql.ResultSet;
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


        //TODO
        if(commandName!=null && commandName.equals("login")){
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            Optional<User> optionalUser = new UserDaoImpl().selectUserByLoginAndPassword(login, password);
            User user = optionalUser.get();
            System.out.println(user);

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
