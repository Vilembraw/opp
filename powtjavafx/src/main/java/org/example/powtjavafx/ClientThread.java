package org.example.powtjavafx;


import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private final Socket socket;
    private final Server server;
    private PrintWriter out;

    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out = new PrintWriter(socket.getOutputStream(), true);

            // Wyślij zapisane kropki nowemu klientowi
            for (Dot dot : server.getSavedDots()) {
                send(dot.toMessage());
            }

            String message;
            while ((message = in.readLine()) != null) {
                server.broadcast(message);
                server.saveDot(Dot.fromMessage(message));
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            server.removeClient(this);
        }
    }

    public void send(String message) {
        out.println(message);
    }
}