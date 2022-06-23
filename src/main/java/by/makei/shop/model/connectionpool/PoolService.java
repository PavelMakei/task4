package by.makei.shop.model.connectionpool;

import by.makei.shop.exception.DbConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

import static by.makei.shop.model.connectionpool.ConnectionFactory.NUMBER_OF_ATTEMPTS;

/**
 * Pool service check connections by timer
 * If it found bad connections it tries to restore pool
 */
public class PoolService extends TimerTask {
    private static final Logger logger = LogManager.getLogger();
    private static final Timer timer = new Timer();
    private final BlockingDeque<ProxyConnection> busyDeque;
    private final BlockingDeque<ProxyConnection> freeDeque;
    private final BlockingDeque<ProxyConnection> forCheckingDeque;


    private PoolService(BlockingDeque<ProxyConnection> freeDeque,
                        BlockingDeque<ProxyConnection> busyDeque,
                        BlockingDeque<ProxyConnection> forCheckingDeque) {
        this.freeDeque = freeDeque;
        this.busyDeque = busyDeque;
        this.forCheckingDeque = forCheckingDeque;
    }

    /**
     *
     * @param freeDeque - deque for {@link ProxyConnection} which is free
     * @param busyDeque - deque for {@link ProxyConnection} that is being used
     * @param forCheckingDeque deque for {@link ProxyConnection} which has been marked as forChecking
     *
     * Starts pool service by timer schedule
     */
    static void startPoolService(BlockingDeque<ProxyConnection> freeDeque,
                                 BlockingDeque<ProxyConnection> busyDeque,
                                 BlockingDeque<ProxyConnection> forCheckingDeque) {
        PoolService poolService = new PoolService(freeDeque, busyDeque, forCheckingDeque);
        timer.schedule(poolService, ConnectionFactory.TIMER_SERVICE_DELAY, ConnectionFactory.TIMER_SERVICE_INTERVAL);
        logger.log(Level.INFO, "pool service timer started");
    }

    static void stopPoolService() {
        timer.cancel();
        logger.log(Level.INFO, "pool service timer stopped");
    }


    /*
     * 2. Initialize timer run time<
     * 3. Run timer after pool create <
     * 4. Stop all threads before service <
     * 5. Check deque if it has lost connections <
     * 6. Check connections in busy deque and if it's thread is dead then:
     * 6.1. remove connection from busy deque <
     * 6.2 check this connection <
     * 6.2.1. if it is dead then create a new one and put it into free deque
     * 6.2.2. if it is alive then put it into free deque.
     */


    @Override
    public void run() {
        logger.log(Level.DEBUG, "pool service thread run");
        int gotSemaforePermits = 0;
        int activeConnections = 0;
        try {
            while (gotSemaforePermits < ConnectionFactory.MAX_CONNECTIONS) {
                DbConnectionPool.semaphore.acquire();
                gotSemaforePermits++;
                logger.log(Level.DEBUG, "pool service got permits {} from {}", gotSemaforePermits, ConnectionFactory.MAX_CONNECTIONS);
            }
            logger.log(Level.DEBUG, "pool service got all permits");
            activeConnections = freeDeque.size() + busyDeque.size() + forCheckingDeque.size();
            ArrayList<ProxyConnection> proxyConnectionsBusyList = new ArrayList<>(busyDeque);
            for (int i = 0; i < proxyConnectionsBusyList.size(); i++) {
                ProxyConnection proxyConnection = proxyConnectionsBusyList.get(i);
                if (!proxyConnection.getLastThread().isAlive()) {
                    busyDeque.remove(proxyConnection);
                    forCheckingDeque.put(proxyConnection);
                }
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Thread in pool service has been interrupted! :{}", e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            DbConnectionPool.semaphore.release(gotSemaforePermits);
            logger.log(Level.DEBUG, "pool service {} permits were realized", gotSemaforePermits);
        }

        activeConnections = sortOutForCheckingDequeConnections(activeConnections);

        refillPoolWithNewConnections(activeConnections);
    }

    /**
     * Try to fill pool with {@link ProxyConnection} until ConnectionFactory.MAX_CONNECTIONS
     * @param activeConnections - current amount of {@link ProxyConnection} in the pool
     * If it can't create ANY {@link ProxyConnection} after NUMBER_OF_ATTEMPTS - throws RuntimeException
     */
    private void refillPoolWithNewConnections(int activeConnections) {
        int attemptCounter = 0;
        while (activeConnections < ConnectionFactory.MAX_CONNECTIONS) {
            try {
                freeDeque.put(new ProxyConnection(ConnectionFactory.getConnection()));
                activeConnections++;
            } catch (DbConnectionPoolException e) {
                logger.log(Level.ERROR, "can't create connection. {}", e.getMessage());
                attemptCounter++;
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException ex) {
                    logger.log(Level.ERROR, "Thread in pool service has been interrupted! :{}", ex.getMessage());
                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Thread in pool service has been interrupted! :{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
            if (attemptCounter == NUMBER_OF_ATTEMPTS) {
                if (activeConnections == 0) {
                    logger.log(Level.FATAL, "pool service can't create any connection. Application can't work!");
                    throw new RuntimeException("pool service can't create any connection. Application can't work!");
                }
                return;
            }
        }
    }

    /**
     * Checks connections from forCheckingDeque, close bad connections
     * @param activeConnections - total active connections amount in pool
     * @return total active connections amount in pool
     */
    private int sortOutForCheckingDequeConnections(int activeConnections) {
        ArrayList<ProxyConnection> proxyConnectionForCheckList = new ArrayList<>(forCheckingDeque);
        for (ProxyConnection proxyConnection : proxyConnectionForCheckList) {
            try {
                forCheckingDeque.remove(proxyConnection);
                if (proxyConnection.isValid(10)) {
                    proxyConnection.setForChecking(false);
                    freeDeque.put(proxyConnection);
                } else {
                    logger.log(Level.ERROR, "bad connection closed");
                    activeConnections--;
                    proxyConnection.reallyClose();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "SQL exception while connection close: {}", e.getMessage());
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Thread in pool service has been interrupted! :{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return activeConnections;
    }

}
