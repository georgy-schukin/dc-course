package org.example;

public class Worker extends Thread {
    private final int id;

    public Worker(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Hello, I am worker #" + id);
    }
}
