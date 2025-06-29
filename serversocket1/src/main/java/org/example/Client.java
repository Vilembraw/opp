package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class Client {
    private static final String SERVER = "localhost";
    private static final int PORT = 2137;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER, PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to server at " + socket.getRemoteSocketAddress());

            // Thread for receiving messages
            Thread receiver = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    if (!socket.isClosed()) {
                        System.out.println("Connection lost!");
                    }
                }
            });
            receiver.setDaemon(true);
            receiver.start();

            // Main thread for sending messages
            String input;
            while (!Objects.equals(input = userInput.readLine(), "/quit")) {
                if (!input.trim().isEmpty()) {
                    out.println(input);
                }
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Client closed");
        }
    }
}
