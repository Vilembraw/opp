package org.example.javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try{
            String message;
            while((message = in.readLine()) != null) {
                System.out.println("Otrzymano " + message);
                Server.broadcast(message);
            }
        }catch (Exception e) {

        }
    }
}
