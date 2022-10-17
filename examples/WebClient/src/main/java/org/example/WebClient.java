package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WebClient {
    public static void main(String[] args) {
        String host = args.length > 1 ? args[1] : "localhost";
        int port = args.length > 2 ? Integer.parseInt(args[0]) : 8080;

        try {
            CompletableFuture<String> future = getURI(makeUri(host, port, "index.html"));
            System.out.println("Got response:");
            System.out.println(future.get());
        }
        catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    public static String makeUri(String host, int port, String path) {
        return "http://" + host + ":" + port + "/" + path;
    }

    public static CompletableFuture<String> getURI(String uri) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("User-Agent", "WebClient")
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
