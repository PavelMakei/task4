package by.makei.shop.controller;

import by.makei.shop.model.connectionpool.DBConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Controller", value = "/controller")
public class Controller2 extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private DBConnectionPool connectionPool;

    public void init() {
        //TODO connectionPool get instance
        connectionPool = DBConnectionPool.getInstance();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try {
            request.getRequestDispatcher("view/Main.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setContentType("text/html");
        String login = request.getParameter("login");
        Connection con = connectionPool.takeConnection();
        String firstName = "not found";
        String lastName = "not found";
        String password = "not found";



        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        String sql = "SELECT first_name, last_name, password FROM lightingshop.users WHERE login = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,login);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String resultFirstName = "" ;
        String resultLastName = "" ;
        String resultPassword = "";

        try {
            if (resultSet.next()) {
                resultFirstName = resultSet.getString(1);
                resultLastName = resultSet.getString(2);
                resultPassword = resultSet.getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       request.setAttribute("FIRSTNAME", resultFirstName);
       request.setAttribute("LASTNAME", resultLastName);
       request.setAttribute("PASSWORD", resultPassword);

        try {
            request.getRequestDispatcher("view/Main.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }
}
