package org.example;

import java.rmi.Naming;

public class RMIClient {
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        String SERVICE_PATH = "//" + hostName + ":" + port + "/Service";

        try {
            System.setProperty(RMI_HOSTNAME, hostName);
            Service service = (Service) Naming.lookup(SERVICE_PATH);

            String str = service.pollElem();
            if (str == null) {
                System.out.println("Received none!");
            } else {
                System.out.println("Received: " + str);
                service.addElem(str + "!");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
