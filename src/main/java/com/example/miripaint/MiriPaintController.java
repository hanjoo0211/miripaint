package com.example.miripaint;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class MiriPaintController implements Initializable {
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private Tool tool = Tool.PENCIL;
    private double startX, startY, endX, endY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        setCanvas();
        useTool();
    }

    private void setCanvas(){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 960, 540);
    }

    public void setTool(ActionEvent event){
        Button button = (Button)event.getSource();
        String nowTool = button.getUserData().toString();
        tool = Tool.valueOf(nowTool);
        useTool();
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
            case CIRCLE:
                drawCircle();
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
        gc.setLineWidth(1);
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
        gc.setLineWidth(1);
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
        gc.setLineWidth(1);
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

    private void drawCircle(){
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
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