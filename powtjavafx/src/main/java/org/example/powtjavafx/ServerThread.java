package org.example.powtjavafx;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class ServerThread {
    private final Socket socket;
    private PrintWriter out;
    private Consumer<Dot> dotConsumer;

    public ServerThread(String address, int port) throws IOException {
        this.socket = new Socket(address, port);
        new Thread(this::listen).start();
    }

    private void listen() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) {
                Dot dot = Dot.fromMessage(message);
                if (dotConsumer != null) {
                    dotConsumer.accept(dot);
                }
            }
        } catch (IOException e) {
            System.err.println("Server connection error: " + e.getMessage());
        }
    }

    public void send(Dot dot) {
        if (out == null) {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.err.println("Error creating output stream: " + e.getMessage());
                return;
            }
        }
        out.println(dot.toMessage());
    }

    public void setDotConsumer(Consumer<Dot> dotConsumer) {
        this.dotConsumer = dotConsumer;
    }
}