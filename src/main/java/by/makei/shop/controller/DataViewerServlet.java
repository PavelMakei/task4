package by.makei.shop.controller;


import by.makei.shop.model.connectionpool.DbConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimeZone;

public class DataViewerServlet extends HttpServlet {

  Calendar now = Calendar.getInstance();
  TimeZone timeZone = now.getTimeZone();
  String timeZoneName = timeZone.getID();



  /**Load JDBC driver*/
  public void init() {
  }
  /**Process the HTTP Get request*/
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");

    PrintWriter out = response.getWriter();

    String sql = "SELECT first_name, last_name, login, password FROM lightingshop.users";
    Connection con = DbConnectionPool.getInstance().takeConnection();
    Statement s = null;
    try {
      s = con.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      ResultSet rs = s.executeQuery(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    doGet(request, response);
  }
}
