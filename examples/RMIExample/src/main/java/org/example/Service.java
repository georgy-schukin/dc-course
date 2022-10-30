package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for a service which will be accessible remotely
 */
public interface Service extends Remote {
    String pollElem() throws RemoteException;
    void addElem(String str) throws RemoteException;
}
