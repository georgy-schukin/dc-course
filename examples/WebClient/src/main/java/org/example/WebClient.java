package org.example;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebClient {
    public static void main(String[] args) {
        String host = args.length > 1 ? args[1] : "localhost";
        int port = args.length > 2 ? Integer.parseInt(args[0]) : 8080;


        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://" + host + ":" + port + "/index.html"))
                    .header("User-Agent", "WebClient")
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Got response:");
            System.out.println(response.body());
            /*Socket socket = new Socket(host, port);
            Request request = new Request("/index.html");
            request.send(socket);
            Response response = new Response();
            response.receive(socket);
            System.out.println("Got response:");
            System.out.println(response);
            socket.close();*/
        }
        catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
