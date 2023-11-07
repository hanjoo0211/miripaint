package com.example.miripaint;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;


public class MiriPaintController implements Initializable {
    @FXML private Canvas canvas;
    private GraphicsContext gc;
    private Tool tool = Tool.PENCIL;
    @FXML private Label toolLabel;
    @FXML Slider lineWidthSlider;
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
        gc.setStroke(Color.BLACK);
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
        gc.setStroke(Color.BLACK);
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
        gc.setStroke(Color.BLACK);
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
        gc.setStroke(Color.BLACK);
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