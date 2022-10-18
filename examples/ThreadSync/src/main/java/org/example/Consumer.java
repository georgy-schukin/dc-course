package org.example;

public class Consumer<T> extends Thread {
    private final int id;
    private final ThreadSafeQueue<T> queue;

    public Consumer(int id, ThreadSafeQueue<T> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Wait for new element.
                T elem = queue.pop();

                // Stop consuming if null is received.
                if (elem == null) {
                    return;
                }

                // Process element.
                System.out.println(id + ": get item: " + elem);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
