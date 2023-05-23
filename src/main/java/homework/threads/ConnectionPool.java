package homework.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    public static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool connectionPool;

    private final int POOL_SIZE = 5;
    private final List<Connection> pool;
    private final BlockingQueue<Connection> poolQueue;       // available connections within the pool

    private ConnectionPool() {
        pool = new ArrayList<>(POOL_SIZE);
        poolQueue = new ArrayBlockingQueue<>(POOL_SIZE);
    }

    public static ConnectionPool getConnectionPool() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public synchronized Connection requestConnection() {
        if (pool.size() < POOL_SIZE) {
            Connection newConnection = new Connection(pool.size() + 1);
            pool.add(newConnection);
            poolQueue.add(newConnection);
        }
        return getConnection();
    }

    private Connection getConnection() {
        try {
            return poolQueue.take();
        } catch (InterruptedException e) {
            LOGGER.error("\nInterrupted Exception");
        }
        return null;
    }

    public synchronized void releaseConnection(Connection connection) {
        poolQueue.add(connection);
        LOGGER.info("\nRELEASING connection:" + connection.getId());
    }

    public int getPoolSize() {
        return POOL_SIZE;
    }
}
