package by.makei.shop.model.connectionpool;

import by.makei.shop.exception.DbConnectionPoolException;
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

/**
 * Get properties from file and create connection
 */

class ConnectionFactory {//ограничить доступ извне
    private static final Logger logger = LogManager.getLogger();
    private static final String FILE_NAME = "sql/sql_config.properties";
    private static final String DB_URL;
    private static final String PROPERTIES_FILE;
    private static final Properties properties = new Properties();
    public static final int MAX_CONNECTIONS;
    public static final int TIMER_SERVICE_INTERVAL;
    public static final int TIMER_SERVICE_DELAY = 0;
    public static final int NUMBER_OF_ATTEMPTS;

    static {
        URL resource = getUrl();
        PROPERTIES_FILE = resource.getFile();
        loadPropertyFromFile();
        DB_URL = properties.getProperty("db_url", "jdbc:mysql://localhost:3306/lightingshop");
        MAX_CONNECTIONS = Integer.parseInt(properties.getProperty("max_connections", "8"));
        String driverClassName = properties.getProperty("db_driver", "com.mysql.cj.jdbc.Driver");
        TIMER_SERVICE_INTERVAL = Integer.parseInt(properties.getProperty("timer_service_interval", "1000"));
        NUMBER_OF_ATTEMPTS = Integer.parseInt(properties.getProperty("number_of_attempts", "3"));

        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "ConnectionFactory SQL driver can't be loaded - {}", driverClassName);
            throw new RuntimeException("ConnectionFactory SQL driver class can't be read", e);
        }
        setServerTimeZoneProperty();
    }

    private ConnectionFactory() {}
    //friendly methods
    static Connection getConnection() throws DbConnectionPoolException {
        try {
            return DriverManager.getConnection(DB_URL, properties);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "ConnectionFactory getConnection create error", e);
            throw new DbConnectionPoolException("ConnectionFactory connection create error", e);
        }
    }

    private static void loadPropertyFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            logger.log(Level.FATAL, "ConnectionFactory loadPropertyFromFile file - {} - can't be read", FILE_NAME);
            throw new RuntimeException("SQL config property file can't be read", e);
        }
    }

    private static void setServerTimeZoneProperty() {
        if (Boolean.valueOf(properties.getProperty("set_serverTimezone_localTimezone", "true"))) {
            Calendar now = Calendar.getInstance();
            TimeZone timeZone = now.getTimeZone();
            String timeZoneName = timeZone.getID();
            properties.setProperty("serverTimezone", timeZoneName);
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

}
