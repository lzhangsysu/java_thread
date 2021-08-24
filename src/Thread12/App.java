package Thread12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();

        // call 200 threads
        for (int i = 0; i < 200; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Connection.getInstance().connect();
                }
            });
        }

        executor.shutdown();

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
