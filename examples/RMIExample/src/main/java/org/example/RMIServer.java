package org.example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        try {
            System.setProperty(RMI_HOSTNAME, hostName);

            // Create service for RMI
            Service service = new ServiceImpl();
            // Init service
            service.addElem("Hello, world");

            String serviceName = "Service";

            System.out.println("Initializing " + serviceName);

            Registry registry = LocateRegistry.createRegistry(port);
            // Make service available for RMI
            registry.rebind(serviceName, service);

            System.out.println("Start " + serviceName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}