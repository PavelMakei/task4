package by.makei.shop.model.connectionpool;

import by.makei.shop.exception.DbPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;


public class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger();
    private static final Properties PROP = new Properties();
    private static final String FILE_NAME = "sql_config.properties";
    private static final String DB_URL;
    private static final String PROPERTIES_FILE;
    private static final int MAX_CONNECTIONS;

    static {
        URL resource = getUrl();
        PROPERTIES_FILE = resource.getFile();
        loadPropertyFromFile();
        DB_URL = PROP.getProperty("db_url");
        MAX_CONNECTIONS = Integer.parseInt(PROP.getProperty("max_connections", "8"));
        loadSqlDriver();
        setServerTimeZoneProperty();
    }

    private ConnectionFactory() {
    }

    private static void loadPropertyFromFile() {
        try {
            PROP.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            logger.log(Level.FATAL, "file - {} - can't be read", FILE_NAME);
            throw new RuntimeException("SQL config property file can't be read", e);
        }
    }

    private static void loadSqlDriver() {
        String driverClassName = PROP.getProperty("db_driver");
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "SQL driver can't be loaded - {}", driverClassName);
            throw new RuntimeException("SQL driver class can't be read", e);
        }
    }

    private static void setServerTimeZoneProperty() {
        if (Boolean.valueOf(PROP.getProperty("set_serverTomezone_localTimezone"))) {
            Calendar now = Calendar.getInstance();
            TimeZone timeZone = now.getTimeZone();
            String timeZoneName = timeZone.getID();
            PROP.setProperty("serverTimezone", timeZoneName);
        }
    }

    private static URL getUrl() {
        ClassLoader classLoader = ConnectionFactory.class.getClassLoader();
        URL resource = classLoader.getResource(FILE_NAME);
        if (resource == null) {
            logger.log(Level.FATAL, "file - {} - not found!", FILE_NAME);
            throw new RuntimeException("file not found! :" + FILE_NAME);
        }
        return resource;
    }

    //friendly methods

    static Connection getConnection() throws DbPoolException {
        try {
            return DriverManager.getConnection(DB_URL, PROP);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "connection create error", e);
            throw new DbPoolException("connection create error", e);
        }
    }

    static int getMaxConnections() {
        return MAX_CONNECTIONS;
    }

}
