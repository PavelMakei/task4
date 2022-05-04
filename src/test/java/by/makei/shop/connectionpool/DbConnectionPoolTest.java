package by.makei.shop.connectionpool;

import by.makei.shop.model.connectionpool.DbConnectionPool;
import by.makei.shop.model.connectionpool.ProxyConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class DbConnectionPoolTest {
    public DbConnectionPool dbConnectionPool;

    @BeforeEach
    @Test
    void getInstanceTest() {
        dbConnectionPool = DbConnectionPool.getInstance();
        assert (dbConnectionPool != null);
    }

    @Test
    void takeConnectionTest() throws SQLException {
        ProxyConnection connection = dbConnectionPool.takeConnection();
        assert (connection.isValid(100));
        dbConnectionPool.shutdown();
    }


    @Test
    void returnConnection() throws SQLException {
        ProxyConnection connection = dbConnectionPool.takeConnection();
        assert (dbConnectionPool.returnConnection(connection));
        dbConnectionPool.shutdown();
    }

    @Test
    void shutdownTest() throws SQLException {
       assert (dbConnectionPool.shutdown());
        dbConnectionPool.shutdown();
    }
}