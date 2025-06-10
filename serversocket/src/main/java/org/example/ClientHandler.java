package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final List<ClientHandler> clients;
    private PrintWriter out;
    private String login;
    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    public void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public void getOnlineClients(){
        this.sendMessage("Lista zalogowanych clientow");
        for (ClientHandler client : clients) {
            this.sendMessage(client.login);
        }
    }

    public void whisper(String message, String recipient) {
        for (ClientHandler client : clients) {
            if (client.login.equals(recipient)) {
                client.sendMessage(this.login + " whispered you: " + message);
                return;
            }
        }

    }

    public void parseLine(String line) {
        if(line.startsWith("/w ")){
            String[] split = line.split(" ",3 );
            String recipient = split[1];
            String message = split[2];
            whisper(message,recipient);

        }
        else if(line.equals("/online")){
            getOnlineClients();
        }
        else{
            broadcast(this.login + ": " + line);
        }
    }

    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Enter your login");
            String message = in.readLine();
            this.login = message;
            broadcast(this.login + " polaczyl sie");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("<Server> " +this.login + ":" + inputLine);
//                out.println("Sent: " + inputLine);
                parseLine(inputLine);
            }
        } catch (Exception e){
            System.err.println("Communication Error" + e.getMessage());
        } finally {

            try{
                broadcast(this.login + " rozlaczyl sie");
                socket.close();
            } catch (Exception e) {
                System.err.println("Error closing connection" + e.getMessage());
            }finally {
                clients.remove(this);
            }

        }
    }
}

