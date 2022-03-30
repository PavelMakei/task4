package by.makei.shop.connectionpool;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.LinkedList;


public class DBConnectionPool {

    private static DBConnectionPool instance;
    private String URL;
    private String user;
    private String password;
    private String driverName;
    private static String CONFIG_XML = "config.xml";
    
    private Deque<Connection> deque;




    public static DBConnectionPool getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return new DBConnectionPool();
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (!deque.isEmpty()) {
            while (!deque.isEmpty()) {
                Connection connection = deque.poll();
                if (connection.isValid(500)) {
                    return connection;
                }
                return DriverManager.getConnection(URL, user, password);
            }
        }
        return DriverManager.getConnection(URL, user, password);
    }

    public synchronized void freeConnection(Connection connection) {
        try {
            if(!connection.isClosed()) {
                deque.add(connection);
            }
        } catch (SQLException e) {
            return;
        }
    }
}
