package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientGui extends Application {
    private final TextArea chatArea = new TextArea();
    private final TextField inputField = new TextField();
    private final ListView<String> userList = new ListView();
    private final Button sendButton = new Button("Send");

    private PrintWriter out;

    public void sendMessage() {
        String message = inputField.getText();
        if(!message.isEmpty()) {
//           chatArea.appendText(message+"\n");
           inputField.clear();
           out.println(message);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Socket socket = new Socket("localhost",2137);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        ClientReceiver receiver = new ClientReceiver(chatArea,socket,userList);
        receiver.start();
        chatArea.setEditable(false);
        userList.getItems().add("User1");
        userList.getItems().add("User2");

        inputField.setOnAction(event -> sendMessage());
        sendButton.setOnAction(event -> sendMessage());
        BorderPane pane = new BorderPane();
        HBox bottomPanel = new HBox(inputField, sendButton);
        VBox rightPanel = new VBox(
                new Label("Uzytkownicy"), userList
        );

        pane.setCenter(chatArea);
        pane.setRight(rightPanel);
        pane.setLeft(bottomPanel);

        Scene scene = new Scene(pane, 600, 400);

        stage.setTitle("asd");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
