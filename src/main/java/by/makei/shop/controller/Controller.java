package by.makei.shop.controller;

import by.makei.shop.model.connectionpool.DBConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.TimeZone;

@WebServlet(name = "Controller2", value = "/controller2")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private DBConnectionPool connectionPool;



    public void init() {
        //TODO connectionPool get instance
        connectionPool = DBConnectionPool.getInstance();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String firstNameStr = request.getParameter("first_name");
        Connection connection = connectionPool.takeConnection();
        String password = "not find";

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;


        String sql = "SELECT user_name, surname, login, password" +
                     " FROM lightingshop.users";
        try {
//      Connection con = DriverManager.getConnection(URL + timeZoneName, USER_NAME, PASSWORD);
            Connection con = connection;
            System.out.println("got connection");

            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);


            rs.close();
            s.close();
            con.close();
        }
        catch (SQLException e) {
        }
        catch (Exception e) {
        }




        try {
            String SQL;
            SQL = "SELECT password FROM lightingshop.users WHERE first_name=?";
            String SQl2 ="SELECT * FROM users WHERE last_name";

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, firstNameStr);
            resultSet = preparedStatement.executeQuery();//empty arguments for prepared statement

            if (resultSet.next()) {
                password = resultSet.getString(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        request.setAttribute("result", password);

        try {
            request.getRequestDispatcher("view/Main.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");


//        String strNum = request.getParameter("num");
//        int resNum = 2 * Integer.parseInt(strNum);
//        request.setAttribute("result", resNum);

        try {
            request.getRequestDispatcher("pages/Main.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }


    }

    public void destroy() {
        logger.log(Level.INFO, "------>Servlet destroyed :" + this.getServletName());
        try {
            connectionPool.shutdown();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "connection pool shutdown SQL exception");
            //TODO ?????????????
            e.printStackTrace();
        }
    }
}