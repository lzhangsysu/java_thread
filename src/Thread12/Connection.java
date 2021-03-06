package Thread12;

import java.util.concurrent.Semaphore;

public class Connection {

    private static Connection instance = new Connection();

    private Semaphore sem = new Semaphore(10, true);

    private int connections = 0;

    // singleton
    private Connection() {

    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();  // can only get 10 connections at given time

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doConnect();
        } finally {
            sem.release();
        }
    }

    public void doConnect() {

        synchronized (this) {
            connections++;
            System.out.println("Current connections: " + connections);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this)  {
            connections--;
        }
    }
}
