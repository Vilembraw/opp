package org.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final static String SERVER_ADDRESS = "localhost";
    private final static int SERVER_PORT = 2137;


    private static final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private static ExecutorService threadpool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("<Server> Listening on " + SERVER_ADDRESS + ":" + SERVER_PORT);
            while(true) {
                System.out.println("<Server> Waiting for connection...");
                Socket socket = serverSocket.accept();
                System.out.println("<Server> Accepted connection from " + socket.getInetAddress() + ":" + socket.getPort());
                ClientHandler clientHandler = new ClientHandler(socket,clients);
                clients.add(clientHandler);
//                threadpool.submit(clientHandler); Future wchodzi w gre, czyli wychwytywanie wyjatkow i trackowanie
                threadpool.execute(clientHandler);
            }
        }catch (Exception e) {
            System.err.println("<Server> Could not listen on " + SERVER_ADDRESS + ":" + SERVER_PORT);
        }finally {
            threadpool.shutdown();
        }
    }
}

// dla obslugi serwera 1 klienta
//
//public static void main(String[] args) {
//    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//        System.out.println("Server started. Waiting for single client...");
//
//        while (true) {
//            Socket clientSocket = serverSocket.accept();
//
//            // Jeśli już jest podłączony klient, odrzuć nowe połączenie
//            if (currentClient != null) {
//                System.out.println("Rejecting connection - only one client allowed");
//                PrintWriter tempOut = new PrintWriter(clientSocket.getOutputStream(), true);
//                tempOut.println("Server busy. Only one connection allowed at a time.");
//                clientSocket.close();
//                continue;
//            }
//
//            System.out.println("Client connected: " + clientSocket.getInetAddress());
//            currentClient = new ClientHandler(clientSocket);
//            new Thread(currentClient).start();
//        }
//    } catch (IOException e) {
//        System.err.println("Server error: " + e.getMessage());
//    }
//}




/*
*
private static final Set<ClientHandler> activeClients = Collections.synchronizedSet(new HashSet<>());
*
potrzebna synchronizacja
*
synchronized(mySet) {
    for (Object item : mySet) { ... }
}

void broadcast(String msg) {
    synchronized(clients) {
        for (ClientHandler client : clients) {
            client.send(msg);
        }
    }
}
*
*
*/