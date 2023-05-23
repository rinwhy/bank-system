package homework.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client extends Thread implements Runnable {

    public static final Logger LOGGER = LogManager.getLogger(Client.class);

    private final int clientID;
    private Connection connection;

    public Client(int clientID) {
        this.clientID = clientID;
    }

    private void clientTask() {
        if (connection != null) {
            LOGGER.info("\nClient " + clientID + " performing task on thread:" + Thread.currentThread().getId());
        } else LOGGER.info("\nConnection is null");
    }

    @Override
    public void run() {
        try {
            this.connection = ConnectionPool.getConnectionPool().requestConnection();
            LOGGER.info("\nClient " + clientID + " acquired connection " + connection.getId());
            clientTask();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ConnectionPool.getConnectionPool().releaseConnection(this.connection);
    }
}
