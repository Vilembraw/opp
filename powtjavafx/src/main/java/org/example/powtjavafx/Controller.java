package org.example.powtjavafx;


import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;


public class Controller {
    @FXML private Canvas canvas;
    @FXML private ColorPicker colorPicker;
    @FXML private Slider radiusSlider;
    @FXML private TextField addressField;
    @FXML private TextField portField;

    private ServerThread serverThread;
    private Server server;

    @FXML
    private void onMouseClicked(MouseEvent event) {
        Color color = colorPicker.getValue();
        int radius = (int) radiusSlider.getValue();
        Dot dot = new Dot(
                (int) event.getX(),
                (int) event.getY(),
                color.toString(),
                radius
        );

        if (serverThread != null) {
            serverThread.send(dot);
        }
    }

    @FXML
    private void onConnectClicked() {
        try {
            serverThread = new ServerThread(
                    addressField.getText(),
                    Integer.parseInt(portField.getText())
            );

            serverThread.setDotConsumer(dot -> {
                javafx.application.Platform.runLater(() -> {
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.setFill(Color.web(dot.color()));
                    gc.fillOval(
                            dot.x() - dot.radius(),
                            dot.y() - dot.radius(),
                            dot.radius() * 2,
                            dot.radius() * 2
                    );
                });
            });
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Connection error: " + e.getMessage()).show();
        }
    }

    @FXML
    private void onStartServerClicked() {
        try {
            int port = Integer.parseInt(portField.getText());
            server = new Server(port);
            new Thread(() -> {
                try {
                    server.start();
                } catch (IOException e) {
                    javafx.application.Platform.runLater(() ->
                            new Alert(Alert.AlertType.ERROR, "Server error: " + e.getMessage()).show());
                }
            }).start();
            onConnectClicked();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid port number").show();
        }
    }
}