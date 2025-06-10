package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ChatClient {
    private static final String SERVER = "localhost";
    private static final int PORT = 2137;


    public static void main(String[] args) {
        try(
            Socket socket = new Socket(SERVER,PORT);

        ){
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to: " + socket.getRemoteSocketAddress());

            Thread receiverThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            receiverThread.setDaemon(true);
            receiverThread.start();



            String inputLine = "";
            while (!Objects.equals(inputLine = reader.readLine(), "exit")) {
                out.println(inputLine);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
