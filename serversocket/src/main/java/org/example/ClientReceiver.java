package org.example;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceiver extends Thread {
    private final TextArea chatArea;
    private final Socket socket;
    private final ListView<String> userList;


    public ClientReceiver(TextArea chatArea, Socket socket, ListView<String> userList) {
        this.chatArea = chatArea;
        this.socket = socket;
        this.userList = userList;
    }


    public void handleMessage(String message) {
        if(message.startsWith("LOGIN")) {
            String[] parts = message.split(" ");
            Platform.runLater( () -> userList.getItems().add(parts[1]));
        }else{
            Platform.runLater( () -> chatArea.appendText(message + "\n"));

        }
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                handleMessage(inputLine);
            }
        }catch (Exception e){
            chatArea.appendText("Blad polacenia " + e.getMessage());
        }
    }
}
