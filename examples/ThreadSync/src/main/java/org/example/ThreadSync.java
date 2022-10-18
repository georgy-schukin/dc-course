package org.example;

public class ThreadSync {
    public static void main(String[] args) {
        int numOfThreads = (args.length > 1 ? Integer.parseInt(args[1]) : 4);
        int numOfItems = (args.length > 2 ? Integer.parseInt(args[2]) : 100);

        ThreadSafeQueue<String> queue = new ThreadSafeQueue<>();

        // Starting consumer threads.
        for (int i = 0; i < numOfThreads; i++) {
            Consumer<String> cons = new Consumer<>(i, queue);
            cons.start();
        }

        // Adding items in the queue for consumers.
        for (int i = 0; i < numOfItems; i++) {
            queue.add("item " + i);
        }

        // Stopping consumers by sending them null values.
        for (int i = 0; i < numOfThreads; i++) {
            queue.add(null);
        }
    }
}