package by.makei.shop.model.connectionpool;

import by.makei.shop.exception.DbPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;


public class DBConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static AtomicReference<DBConnectionPool> instance = new AtomicReference<>();
    private static final int max_connections;
    private static final ReentrantLock lock = new ReentrantLock();

    private BlockingDeque<Connection> freeDeque;
    private BlockingDeque<Connection> busyDeque;

    static {
        max_connections = ConnectionFactory.getMaxConnections();
    }

    {
        freeDeque = new LinkedBlockingDeque<>(max_connections);
        busyDeque = new LinkedBlockingDeque<>(max_connections);
        fillConnectionsIntoPool();
        checkIfPoolEmpty();
    }

    private DBConnectionPool() {
    }

    private void fillConnectionsIntoPool() {
        //TODO remake while  freeDeque.size() = max_connections ?
        for (int i = 0; i < max_connections; i++) {
            try {
                freeDeque.add(ConnectionFactory.getConnection());
            } catch (DbPoolException e) {
                logger.log(Level.ERROR, "Can't create connection", e);
                //TODO if throw new exception?
            }
        }
    }

    private void checkIfPoolEmpty() {
        if (freeDeque.isEmpty()) {
            logger.log(Level.FATAL, "Can't create connections");
            throw new RuntimeException("Can't create connections");
        }
    }

    public static DBConnectionPool getInstance() { // enum and synchronized singletons are forbidden by task
        if (instance.get() == null) {
            lock.lock();
            try {
                if (instance.get() == null) {
                    instance.set(new DBConnectionPool());
                    logger.log(Level.INFO, "new DBConnectionPool is created");
                }
            } finally {
                lock.unlock(); // снятие блокировки
            }
        }
        return instance.get();
    }


    public Connection takeConnection() {
        Connection connection = null;
        try {
            connection = freeDeque.take();
            busyDeque.put(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Thread has been interrupted! :{}", e.getMessage());
            Thread.currentThread().interrupt(); // TODO what is the purpose?
        }
        return connection;
    }


    public boolean returnConnection(Connection connection) {
        boolean result = false;
        try {
            freeDeque.put(connection);
            busyDeque.remove(connection);
            result = true;
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Thread has been interrupted! :{}", e.getMessage());
            Thread.currentThread().interrupt(); // TODO what is the purpose?
        }
        return result;
    }

    public boolean shutdown() throws SQLException {

        while (!freeDeque.isEmpty()) {
            try {
                freeDeque.take().close();
                logger.log(Level.INFO, "free connection is closed");
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Thread has been interrupted! :{}", e.getMessage());
                Thread.currentThread().interrupt(); // TODO what is the purpose?
            }
        }
        while (!busyDeque.isEmpty()) {
            try {
                busyDeque.take().close();
                logger.log(Level.INFO, "busy connection is closed");
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Thread has been interrupted! :{}", e.getMessage());
                Thread.currentThread().interrupt(); // TODO what is the purpose?
            }
        }
        deregisterDrivers();// TODO If it is necessary?
        logger.log(Level.INFO, "DBConnectionPool is closed ");
        return true;
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
                logger.log(Level.DEBUG, "Deregister driver.");
            } catch (SQLException e) {
                logger.log(Level.ERROR, "The driver was not removed");
            }
        });
    }

}
