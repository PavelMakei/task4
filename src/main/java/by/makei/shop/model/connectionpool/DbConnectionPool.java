package by.makei.shop.model.connectionpool;

import by.makei.shop.exception.DbConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;


public final class DbConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicReference<DbConnectionPool> instance = new AtomicReference<>();
    private static final ReentrantLock getterLock = new ReentrantLock();
    static Semaphore semaphore = new Semaphore(ConnectionFactory.MAX_CONNECTIONS);


    private BlockingDeque<ProxyConnection> freeDeque = new LinkedBlockingDeque<>(ConnectionFactory.MAX_CONNECTIONS);
    private BlockingDeque<ProxyConnection> busyDeque = new LinkedBlockingDeque<>(ConnectionFactory.MAX_CONNECTIONS);
    private BlockingDeque<ProxyConnection> forCheckingDeque = new LinkedBlockingDeque<>(ConnectionFactory.MAX_CONNECTIONS);

    private DbConnectionPool() {
        fillConnectionsIntoPool();
        if (freeDeque.size() < ConnectionFactory.MAX_CONNECTIONS) {
            logger.log(Level.ERROR, "Attention!!! {} connections from expected {} were created!",
                    freeDeque.size(), ConnectionFactory.MAX_CONNECTIONS);
        }
        checkIfPoolEmpty();
        if (ConnectionFactory.IS_TIMER_SERVICE_ON) {
            PoolService.startPoolService(this, freeDeque, busyDeque, forCheckingDeque);
        }
    }

    public static DbConnectionPool getInstance() { // enum and synchronized singletons are forbidden by task
        if (instance.get() == null) {
            getterLock.lock();
            try {
                if (instance.get() == null) {
                    instance.set(new DbConnectionPool());
                    logger.log(Level.INFO, "new DBConnectionPool is created");
                }
            } finally {
                getterLock.unlock(); // снятие блокировки
            }
        }
        return instance.get();
    }

    public Connection takeConnection() {
//        ProxyConnection connection = null;
//        try {
//            connection = freeDeque.take();
//            busyDeque.put(connection);
//            connection.setLastThread(Thread.currentThread());
//        } catch (InterruptedException e) {
//            logger.log(Level.ERROR, "Thread has been interrupted! :{}", e.getMessage());
//            Thread.currentThread().interrupt();
//        }
//        return connection;

        ProxyConnection connection = null;

        try {
            semaphore.acquire();
            connection = freeDeque.take();
            connection.setLastThread(Thread.currentThread());
            busyDeque.put(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Thread in take connection has been interrupted! :{}", e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
        return connection;
    }

    public boolean returnConnection(Connection connection) {
        boolean result = false;
        if (!(connection instanceof ProxyConnection)) {
            logger.log(Level.ERROR, "incorrect connection!");
            return false;
        }
        try {
            getterLock.lock();// method "remove" isn't thread safe
            semaphore.acquire();
            busyDeque.remove(connection);//дешевле создать новый, чем искать дубли в очередях
            if (((ProxyConnection) connection).isForChecking()) {
                forCheckingDeque.put((ProxyConnection) connection);
            } else {
                freeDeque.put((ProxyConnection) connection);
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

    public boolean shutdown() {
        if (ConnectionFactory.IS_TIMER_SERVICE_ON) {
            PoolService.stopPoolService();
        }

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

    private void fillConnectionsIntoPool() {

        for (int i = (freeDeque.size() + busyDeque.size()); i < ConnectionFactory.MAX_CONNECTIONS; i++) {
            try {
                freeDeque.add(new ProxyConnection(ConnectionFactory.getConnection()));
                logger.log(Level.DEBUG, "connection created. freeDeque size = {}, busy deQue = {}", freeDeque.size(), busyDeque.size());
            } catch (DbConnectionPoolException e) {
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
