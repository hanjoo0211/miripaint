package com.example.miripaint;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;


public class MiriPaintController implements Initializable {
    @FXML private Canvas canvas;
    @FXML private Label toolLabel;
    @FXML private Slider lineWidthSlider;
    @FXML private ColorPicker colorPicker;
    private GraphicsContext gc;
    private Tool tool = Tool.PENCIL;
    private double startX, startY, endX, endY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        setCanvas();
        useTool();
        lineWidthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setLineWidth();
        });
    }

    private void setCanvas(){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 960, 540);
    }

    public void setTool(ActionEvent event){
        Button button = (Button)event.getSource();
        String nowTool = button.getUserData().toString();
        toolLabel.setText(nowTool);
        tool = Tool.valueOf(nowTool);
        useTool();
    }

    public void setLineWidth(){
        gc.setLineWidth(lineWidthSlider.getValue());
    }

    public void setColor(){
        gc.setStroke(colorPicker.getValue());
    }

    private void useTool(){
        disableTool();
        switch(tool){
            case SELECT:
                break;
            case PENCIL:
                drawPencil();
                break;
            case LINE:
                drawLine();
                break;
            case RECTANGLE:
                drawRectangle();
                break;
            case ELLIPSE:
                drawEllipse();
                break;
        }
    }

    private void disableTool(){
        canvas.setOnMousePressed(null);
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseReleased(null);
    }

    private void drawPencil(){
        canvas.setOnMousePressed(e -> {
            gc.beginPath();
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });
        canvas.setOnMouseDragged(e -> {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });
        canvas.setOnMouseReleased(e -> {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
            gc.closePath();
        });
    }

    private void drawLine(){
        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();
            gc.strokeLine(startX, startY, endX, endY);
        });
    }

    private void drawRectangle(){
        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();
            double leftX = Math.min(startX, endX);
            double topY = Math.min(startY, endY);
            gc.strokeRect(leftX, topY, Math.abs(endX - startX), Math.abs(endY - startY));
        });
    }

    private void drawEllipse(){
        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();
            double leftX = Math.min(startX, endX);
            double topY = Math.min(startY, endY);
            gc.strokeOval(leftX, topY, Math.abs(endX - startX), Math.abs(endY - startY));
        });
    }
}