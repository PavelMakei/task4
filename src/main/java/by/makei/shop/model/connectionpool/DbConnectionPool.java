package by.makei.shop.model.connectionpool;

import by.makei.shop.exception.DbConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * contains code of thread safe ProxyConnection{@link ProxyConnection} pool
 */
public final class DbConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicReference<DbConnectionPool> instance = new AtomicReference<>();
    private static final ReentrantLock getterLock = new ReentrantLock();
    static Semaphore semaphore = new Semaphore(ConnectionFactory.MAX_CONNECTIONS);


    private BlockingDeque<ProxyConnection> freeDeque = new LinkedBlockingDeque<>(ConnectionFactory.MAX_CONNECTIONS);
    private BlockingDeque<ProxyConnection> busyDeque = new LinkedBlockingDeque<>(ConnectionFactory.MAX_CONNECTIONS);
    private BlockingDeque<ProxyConnection> forCheckingDeque = new LinkedBlockingDeque<>(ConnectionFactory.MAX_CONNECTIONS);

    /**
     * Constructor creates a BlockingDeque of free {@link ProxyConnection}
     * checks if it was created and filled
     * if no connection were created - closes application by throw new RuntimeException
     */

    private DbConnectionPool() {
        fillConnectionsIntoPool();
        if (freeDeque.size() < ConnectionFactory.MAX_CONNECTIONS) {
            logger.log(Level.ERROR, "Attention!!! {} connections from expected {} were created!",
                    freeDeque.size(), ConnectionFactory.MAX_CONNECTIONS);
        }
        checkIfPoolEmpty();
        PoolService.startPoolService( freeDeque, busyDeque, forCheckingDeque);
    }

    /**
     * It is thread safe singleton
     * check if instance of DbConnectionPoll not exists and create new, if already exists - return it
     * @return instance of DbConnectionPoll
     */
    public static DbConnectionPool getInstance() { // enum and synchronized singletons are forbidden by task
        if (instance.get() == null) {
            getterLock.lock();
            try {
                if (instance.get() == null) {
                    instance.set(new DbConnectionPool());
                    logger.log(Level.INFO, "new DBConnectionPool is created");
                }
            } finally {
                getterLock.unlock();
            }
        }
        return instance.get();
    }

    public ProxyConnection takeConnection() {
        ProxyConnection proxyConnection = null;
        try {
            semaphore.acquire();
            proxyConnection = freeDeque.take();
            proxyConnection.setLastThread(Thread.currentThread());
            busyDeque.put(proxyConnection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Thread in take connection has been interrupted! :{}", e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
        return proxyConnection;
    }

    /**
     * Checks if connection has true field forChecking put this connection into forCheckingDeque, else - into freeDeque
     * Uses semafore {@link Semaphore} to check if the method free from other threads
     * @param connection {@link ProxyConnection}
     * @return boolean as result
     */
    public boolean returnConnection(ProxyConnection connection) {
        boolean result = false;
        if (connection == null) {
            logger.log(Level.ERROR, "returnConnection connection is null!");
            return false;
        }
        try {
            getterLock.lock();// method "remove" isn't thread safe
            semaphore.acquire();
            busyDeque.remove(connection);
            if ((connection).isForChecking()) {
                forCheckingDeque.put(connection);
            } else {
                freeDeque.put( connection);
            }
            result = true;
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Thread in return connection has been interrupted! :{}", e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            getterLock.unlock();
            semaphore.release();
        }
        return result;
    }

    /**
     * Closes all connections{@link ProxyConnection}
     * @return boolean as result always true
     */
    public boolean shutdown() {
        PoolService.stopPoolService();
        while (!freeDeque.isEmpty()) {
            try {
                freeDeque.take().reallyClose();
                logger.log(Level.INFO, "free connection is closed");
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Thread has been interrupted! :{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        while (!busyDeque.isEmpty()) {
            try {
                busyDeque.take().reallyClose();
                logger.log(Level.INFO, "busy connection is closed");
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Thread has been interrupted! :{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        while (!forCheckingDeque.isEmpty()) {
            try {
                busyDeque.take().reallyClose();
                logger.log(Level.INFO, "forChecking connection is closed");
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Thread has been interrupted! :{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        deregisterDrivers();
        logger.log(Level.INFO, "DBConnectionPool is closed ");
        return true;
    }

    /**
     * Gets new connection from {@link ConnectionFactory}
     * and wraps it with {@link ProxyConnection}
     * fills freDeque until MAX_CONNECTIONS field
     */
    private void fillConnectionsIntoPool() {

        for (int i = (freeDeque.size() + busyDeque.size()); i < ConnectionFactory.MAX_CONNECTIONS; i++) {
            try {
                freeDeque.add(new ProxyConnection(ConnectionFactory.getConnection()));
                logger.log(Level.DEBUG, "connection created. freeDeque size = {}, busy deQue = {}", freeDeque.size(), busyDeque.size());
            } catch (DbConnectionPoolException e) {
                logger.log(Level.ERROR, "Can't create connection", e);
            }
        }
    }

    private void checkIfPoolEmpty() {
        if (freeDeque.isEmpty()) {
            logger.log(Level.FATAL, "Can't create connections");
            throw new RuntimeException("Can't create connections. Stop application");
        }
    }

    /**
     * remove JDBC drivers from application
     */
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
