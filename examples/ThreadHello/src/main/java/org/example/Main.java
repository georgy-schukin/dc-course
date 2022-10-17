package org.example;

public class Main {
    public static void main(String[] args) {
        int numOfThreads = args.length > 1 ? Integer.parseInt(args[1]) : 4;
        for (int i = 0; i < numOfThreads; i++) {
            Worker worker = new Worker(i);
            worker.start();
        }
    }
}