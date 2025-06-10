package org.example.javafx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private final static List<ClientThread> clientList = new CopyOnWriteArrayList<>();
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(6666)) {
            while(true) {
                System.out.println("Waiting for connection...");
                Socket accepted = serverSocket.accept();
                System.out.println("Accepted connection from " + accepted);
                ClientThread clientThread = new ClientThread(accepted);
                clientThread.start();
                clientList.add(clientThread);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void broadcast(String message) {
        for(ClientThread clientThread : clientList) {
            clientThread.send(message);
        }
    }
}
