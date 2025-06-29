package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    List<ClientHandler> list;
    private PrintWriter out;
    private BufferedReader in;
    private String login;

    public ClientHandler(Socket socket, List<ClientHandler> list) {
        this.socket = socket;
        this.list = list;
    }



    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }


    public void broadcastMessage(String message) {
        for (ClientHandler client : list) {
            String m = String.format("[%s] %s", this.login, message);
            client.sendMessage(m);
        }
    }

    public void serverBroadcastMessage(String message) {
        for (ClientHandler client : list) {
            String m = String.format("[%s] %s", "Server", message);
            client.sendMessage(m);
        }
    }

    public void getOnlineList(){
        this.sendMessage("===LIST===");
        for(ClientHandler client : list){
            this.sendMessage(client.login);
        }
        this.sendMessage("==========");
    }


    public void whisper(String message, String whom) {
        for (ClientHandler client : list) {
            if(client.login.equals(whom)){
                String m = String.format("[%s -> %s] %s", this.login, whom, message);
                client.sendMessage(m);
            }
        }
    }

    public void parseMessage(String message) {
        if(message.startsWith("/")){
            // /whisper kogo wiadomosc
            if(message.startsWith("/whisper")){
                String[] parts = message.split(" ",2);
                whisper(parts[2], parts[1]);
            }
            else if(message.startsWith("/online")){
                getOnlineList();
            }
        }else{
            broadcastMessage(message);
        }
    }


    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            this.out = writer;
            this.in = reader;

            out.println("Enter your login: ");
            this.login = in.readLine();
            System.out.println(login + " logged in");

            serverBroadcastMessage("Hello, " + login + "!");

            String inputMessage;
            while ((inputMessage = in.readLine()) != null) {
                parseMessage(inputMessage);
            }

        }catch(Exception e){
            System.err.println("<Server> Could not open input stream");
        }
    }
}
