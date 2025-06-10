package org.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int PORT = 2137;
    private static final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private static ExecutorService threadpool = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Listening on port " + PORT);
            while (true) {
                System.out.println("Waiting for client on port " + PORT);
                Socket socket = serverSocket.accept();
                System.out.println("Accepted client on port " + PORT);
                ClientHandler clientHandler = new ClientHandler(socket, clients);
                clients.add(clientHandler);
                threadpool.submit(clientHandler);
            }
        }catch(Exception e) {
            System.err.println("Could not listen on port: " + PORT);
        }finally{
            threadpool.shutdown();
        }

    }
}
