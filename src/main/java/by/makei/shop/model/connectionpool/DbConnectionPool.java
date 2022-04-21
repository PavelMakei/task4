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
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;


public final class DbConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicReference<DbConnectionPool> instance = new AtomicReference<>();
    private static final ReentrantLock lock = new ReentrantLock();

    private BlockingDeque<ProxyConnection> freeDeque = new LinkedBlockingDeque<>(ConnectionFactory.MAX_CONNECTIONS);
    private BlockingDeque<ProxyConnection> busyDeque = new LinkedBlockingDeque<>(ConnectionFactory.MAX_CONNECTIONS);


    private DbConnectionPool() {
        fillConnectionsIntoPool();
        if (freeDeque.size() < ConnectionFactory.MAX_CONNECTIONS) {
            logger.log(Level.ERROR, "Attention!!! {} connections from expected {} were created!",
                    freeDeque.size(), ConnectionFactory.MAX_CONNECTIONS);
        }
        checkIfPoolEmpty();
        if (ConnectionFactory.IS_TIMER_SERVICE_ON){
            PoolService.startPoolService(this, freeDeque, busyDeque);
        }
    }

    public static DbConnectionPool getInstance() { // enum and synchronized singletons are forbidden by task
        if (instance.get() == null) {
            lock.lock();
            try {
                if (instance.get() == null) {
                    instance.set(new DbConnectionPool());
                    logger.log(Level.INFO, "new DBConnectionPool is created");
                }
            } finally {
                lock.unlock(); // снятие блокировки
            }
        }
        return instance.get();
    }

    public Connection takeConnection() {
        while (!PoolService.isPoolFree.get()){//replace with semafor
            Thread.yield();
        }
        ProxyConnection connection = null;
        try {
            connection = freeDeque.take();
            busyDeque.put(connection);
            connection.setLastThread(Thread.currentThread());
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Thread has been interrupted! :{}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public boolean returnConnection(Connection connection) {
        while (!PoolService.isPoolFree.get()){
            Thread.yield();
        }
        boolean result = false;
        if (!(connection instanceof ProxyConnection)) {
            logger.log(Level.ERROR, "incorrect connection!");
            return false;
        }
        try {
            lock.lock();// method "remove" isn't thread safe
            freeDeque.put((ProxyConnection) connection);
            busyDeque.remove(connection);
            result = true;
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Thread has been interrupted! :{}", e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return result;
    }

    public boolean shutdown() {
        if(ConnectionFactory.IS_TIMER_SERVICE_ON){
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
        deregisterDrivers();
        logger.log(Level.INFO, "DBConnectionPool is closed ");
        return true;
    }

    private void fillConnectionsIntoPool() {

        for (int i = (freeDeque.size()+ busyDeque.size()); i < ConnectionFactory.MAX_CONNECTIONS; i++) {
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
