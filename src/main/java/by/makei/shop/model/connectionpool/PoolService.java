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
import java.util.concurrent.atomic.AtomicBoolean;

public class PoolService extends TimerTask {
    private static final Logger logger = LogManager.getLogger();
    public static final AtomicBoolean isPoolFree = new AtomicBoolean(true);
    private static final Timer timer = new Timer(); //TODO static?
    private static ProxyConnection proxyConnection;
    private final BlockingDeque<ProxyConnection> busyDeque;
    private final BlockingDeque<ProxyConnection> freeDeque;
    private final BlockingDeque<ProxyConnection> forCheckingDeque;

    private final DbConnectionPool dbConnectionPool;


    private PoolService(DbConnectionPool dbConnectionPool, BlockingDeque<ProxyConnection> freeDeque,
                        BlockingDeque<ProxyConnection> busyDeque,
                        BlockingDeque<ProxyConnection> forCheckingDeque) {
        this.dbConnectionPool = dbConnectionPool;
        this.freeDeque = freeDeque;
        this.busyDeque = busyDeque;
        this.forCheckingDeque = forCheckingDeque;
    }

    static void startPoolService(DbConnectionPool dbConnectionPool, BlockingDeque<ProxyConnection> freeDeque,
                                 BlockingDeque<ProxyConnection> busyDeque,
                                 BlockingDeque<ProxyConnection> forCheckingDeque) {
        PoolService poolService = new PoolService(dbConnectionPool, freeDeque, busyDeque, forCheckingDeque);
        timer.schedule(poolService, ConnectionFactory.TIMER_SERVICE_DELAY, ConnectionFactory.TIMER_SERVICE_INTERVAL);
        logger.log(Level.INFO, "pool service timer started");
    }

    static void stopPoolService() {
        timer.cancel();
        logger.log(Level.INFO, "pool service timer stopped");
    }


    /**
     * 1. Инициализировать запуск, таймер он например <
     * 2. Инициализировать время запуска таймера <
     * 3. Запускать после сотворения и наполнения пула <
     * 4. Перед проходом тормозить все потоки
     * 5. Проверить на потеряшки между очередями
     * 6. Проверить коннекшоны в занятой декю и, если поток, его туда положивший мёртв, то:
     * 6.1. удалить коннекшон из занятой декю
     * 6.2 проверить жив ли коннекшон
     * 6.2.1. если мёртв, то создать новый и положить во фри декю
     * 6.2.2. если жив, то положить во фри декю.
     */


    @Override
    public void run() {
        logger.log(Level.INFO, "pool service thread run");
        int gotSemaforePermits = 0;
        int activeConnections = 0;
        try {
            while (gotSemaforePermits < ConnectionFactory.MAX_CONNECTIONS) {
                DbConnectionPool.semaphore.acquire();
                gotSemaforePermits++;
                logger.log(Level.INFO, "pool service got permits {} from {}", gotSemaforePermits, ConnectionFactory.MAX_CONNECTIONS);
            }
            logger.log(Level.INFO, "pool service got all permits");
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
            logger.log(Level.INFO, "pool service {} permits were realized", gotSemaforePermits);
        }
//TODO extract to other method?
        ArrayList<ProxyConnection> proxyConnectionForCheckList = new ArrayList<>(forCheckingDeque);
        for (ProxyConnection proxyConnection : proxyConnectionForCheckList) {
            try {
                forCheckingDeque.remove(proxyConnection);
                if (proxyConnection.isValid(10)) {
                    proxyConnection.setForChecking(false);
                    freeDeque.put(proxyConnection);
                } else {
                    activeConnections--;
                    proxyConnection.reallyClose();
                    //TODO valid close here?
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "SQL exception while connection close: {}", e.getMessage());
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Thread in pool service has been interrupted! :{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        while (activeConnections<ConnectionFactory.MAX_CONNECTIONS){
            try {
                freeDeque.put(new ProxyConnection(ConnectionFactory.getConnection()));
            } catch (DbConnectionPoolException e) {
                //TODO механизм повтора попыток?
                //если нет, то что?

            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Thread in pool service has been interrupted! :{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

}
