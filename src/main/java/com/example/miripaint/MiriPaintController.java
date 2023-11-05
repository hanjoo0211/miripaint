package com.example.miripaint;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.example.miripaint.Tool;

public class MiriPaintController implements Initializable {
    @FXML Canvas canvas;
    GraphicsContext gc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        setCanvas();
        Tool tool = Tool.PENCIL;
        useTool(gc, canvas, tool);
    }

    private void setCanvas(){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 960, 540);
    }

    private void useTool(GraphicsContext gc, Canvas canvas, Tool tool){
        switch(tool){
            case SELECT:
                break;
            case PENCIL:
                drawPencil(gc, canvas);
                break;
            case LINE:
//                drawLine();
                break;
            case RECTANGLE:
                break;
        }
    }


    private void drawPencil(GraphicsContext gc, Canvas canvas){
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
        gc.strokeLine(0, 0, 480, 540);
    }

}