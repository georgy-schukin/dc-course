package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Request {
    private final String url;

    public Request(String url) {
        this.url = url;
    }

    public void send(Socket socket) throws IOException {
        // We are sending HTTP request with GET method.
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println("GET " + url + " HTTP/1.1");
        writer.println("Host: " + socket.getInetAddress().getHostName() + ":" + socket.getPort());
        writer.println("Accept: */*");
        writer.println("User-Agent: WebClient");
        writer.println();
        writer.flush();
    }
}
