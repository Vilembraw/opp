package org.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Canvas canvas;
    @FXML
    private Slider radiusSlider;


    @FXML
    public void onStartServerClicked(ActionEvent actionEvent) {
        System.err.println("Starting server...");
    }

    @FXML
    public void onConnectClicked(ActionEvent actionEvent) {
        System.err.println("Connecting to server...");
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        System.err.println("Mouse clicked..." + mouseEvent);
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        System.err.println("X: " + x + ", Y: " + y);
        double radius = radiusSlider.getValue();
        Color color = colorPicker.getValue();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
