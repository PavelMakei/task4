package by.makei.shop.connectionpool;

import by.makei.shop.model.connectionpool.DBConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class DBConnectionPoolTest {
    public DBConnectionPool dbConnectionPool;

    @BeforeEach
    @Test
    void getInstanceTest() {
        dbConnectionPool = DBConnectionPool.getInstance();
        assert (dbConnectionPool != null);
    }

    @Test
    void takeConnectionTest() throws SQLException {
        Connection connection = dbConnectionPool.takeConnection();
        assert (connection.isValid(100));
        dbConnectionPool.shutdown();
    }


    @Test
    void returnConnection() throws SQLException {
        Connection connection = dbConnectionPool.takeConnection();
        assert (dbConnectionPool.returnConnection(connection));
        dbConnectionPool.shutdown();
    }

    @Test
    void shutdownTest() throws SQLException {
       assert (dbConnectionPool.shutdown());
        dbConnectionPool.shutdown();
    }
}