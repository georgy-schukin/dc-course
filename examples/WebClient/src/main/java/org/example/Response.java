package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Response {
    private String content;

    @Override
    public String toString() {
        return content;
    }

    public void receive(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader( new InputStreamReader(socket.getInputStream(),
                StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\r\n");
        }
        this.content = builder.toString();
    }
}
