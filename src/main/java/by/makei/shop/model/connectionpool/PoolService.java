package by.makei.shop.model.connectionpool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private final DbConnectionPool dbConnectionPool;


    private PoolService(DbConnectionPool dbConnectionPool, BlockingDeque<ProxyConnection> freeDeque, BlockingDeque<ProxyConnection> busyDeque) {
       this.dbConnectionPool = dbConnectionPool;
       this.freeDeque = freeDeque;
       this.busyDeque = busyDeque;
    }

    static void startPoolService(DbConnectionPool dbConnectionPool, BlockingDeque<ProxyConnection> freeDeque, BlockingDeque<ProxyConnection> busyDeque) {
        PoolService poolService = new PoolService(dbConnectionPool,freeDeque,busyDeque);
        timer.schedule(poolService,ConnectionFactory.TIMER_SERVICE_DELAY, ConnectionFactory.TIMER_SERVICE_INTERVAL);
        logger.log(Level.INFO, "pool service timer started");
    }

    static void stopPoolService(){
        timer.cancel();
        logger.log(Level.INFO, "pool service timer stopped");
    }



    /**
     * 1. Инициализировать запуск, таймер он например
     * 2. Инициализировать время запуска таймера
     * 3. Запускать после сотворения и наполнения пула (как?)
     * 4. Перед проходом тормозить все потоки (типа дабл чек?)
     * 5. Проверить на потеряшки между очередями
     * 6. Проверить коннекшоны в занятой декю и, если поток, его туда положивший мёртв, то:
     * 6.1. удалить коннекшон из занятой декю
     * 6.2 проверить жив ли коннекшон
     * 6.2.1. если мёртв, то создать новый и положить во фри декю
     * 6.2.2. если жив, то положить во фри декю.
     */


    @Override
    public void run() {
        //проверить
        System.out.println("++++++++++++++++++++++++++++++++TIMER STARTED");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------TIMER STOPPED");



    }
}
