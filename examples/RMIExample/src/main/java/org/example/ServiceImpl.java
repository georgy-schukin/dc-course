package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of the remote service.
 */
public class ServiceImpl extends UnicastRemoteObject implements Service {
    private final BlockingQueue<String> queue;
    public ServiceImpl() throws RemoteException {
        super();
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public String pollElem() throws RemoteException {
        return this.queue.poll();
    }

    @Override
    public void addElem(String str) throws RemoteException {
        this.queue.add(str);
        System.out.println("Added: " + str);
    }
}
