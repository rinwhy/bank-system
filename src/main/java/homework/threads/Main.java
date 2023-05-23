package homework.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        ExecutorService executor = Executors.newFixedThreadPool(connectionPool.getPoolSize());

        int amountOfClients = 7;

        //using Runnable
        for (int i = 1; i <= amountOfClients; i++) {
            Runnable task = new Client(i);
            executor.execute(task);
        }

//        //using Thread
//        for (int i = 1; i <= amountOfClients; i++) {
//            Thread task = new Client(i);
//            executor.execute(task);
//        }

        executor.shutdown();
    }
}
