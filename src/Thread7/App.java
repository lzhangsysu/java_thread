package Thread7;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);  // 10: maximum queue size

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }


    private static void producer() throws InterruptedException {
        Random random = new Random();

        while (true) {
            queue.put(random.nextInt(100)); // if queue is full, put() will wait until an item is taken from the queue
        }
    }

    private static void consumer() throws InterruptedException {
        Random random = new Random();

        while (true) {
            Thread.sleep(100);

            if (random.nextInt(10) == 0) {
                Integer value = queue.take(); // if queue is empty, take() will wait until an item is added to queue

                System.out.println("Taken value: " + value + "; Queue size is: " + queue.size());
            }
        }
    }

}
