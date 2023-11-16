package com.example.miripaint;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


public class MiriPaintController implements Initializable {

    @FXML
    private Canvas canvas;
    @FXML
    private Label toolLabel;
    @FXML
    private Label selectedShapesLabel;
    @FXML
    private Slider lineWidthSlider;
    @FXML
    private ColorPicker colorPicker;
    private GraphicsContext gc;
    private MiriPaintModel shapes = new MiriPaintModel();
    private HashSet<Shape> selectedShapes = new HashSet<>();
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

    private void setCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 960, 540);
    }

    private void updateCanvas() {
        setCanvas();
        for (Shape shape : shapes.getShapes()) {
            gc.setLineWidth(shape.getLineWidth());
            gc.setStroke(Color.valueOf(shape.getColor()));
            switch (shape.getTool()) {
                case LINE:
                    gc.strokeLine(shape.getStartX(), shape.getStartY(), shape.getEndX(),
                        shape.getEndY());
                    break;
                case RECTANGLE:
                    gc.strokeRect(shape.getStartX(), shape.getStartY(), shape.getEndX(),
                        shape.getEndY());
                    break;
                case ELLIPSE:
                    gc.strokeOval(shape.getStartX(), shape.getStartY(), shape.getEndX(),
                        shape.getEndY());
                    break;
            }
        }
    }

    public void setTool(ActionEvent event) {
        Button button = (Button) event.getSource();
        String nowTool = button.getUserData().toString();
        toolLabel.setText(nowTool);
        tool = Tool.valueOf(nowTool);
        useTool();
    }

    public void setLineWidth() {
        gc.setLineWidth(lineWidthSlider.getValue());
    }

    public void setColor() {
        gc.setStroke(colorPicker.getValue());
    }

    private void useTool() {
        disableTool();
        switch (tool) {
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
            case SELECT:
                selectShape();
                break;
        }
    }

    private void disableTool() {
        canvas.setOnMousePressed(null);
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseReleased(null);
    }

    private void drawPencil() {
        canvas.setOnMousePressed(e -> {
            gc.beginPath();
            gc.lineTo(e.getX(), e.getY());
//            gc.stroke();
        });
        canvas.setOnMouseDragged(e -> {
            gc.lineTo(e.getX(), e.getY());
//            gc.stroke();
        });
        canvas.setOnMouseReleased(e -> {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
            gc.closePath();
        });
    }

    private void drawLine() {
        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();
            Shape line = new Shape(startX, startY, endX, endY, Tool.LINE,
                lineWidthSlider.getValue(), colorPicker.getValue().toString());
            shapes.addShape(line);
            gc.strokeLine(startX, startY, endX, endY);
        });
    }

    private void drawRectangle() {
        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();
            double leftX = Math.min(startX, endX);
            double topY = Math.min(startY, endY);
            Shape rectangle = new Shape(leftX, topY, Math.abs(endX - startX),
                Math.abs(endY - startY), Tool.RECTANGLE, lineWidthSlider.getValue(),
                colorPicker.getValue().toString());
            shapes.addShape(rectangle);
            gc.strokeRect(leftX, topY, Math.abs(endX - startX), Math.abs(endY - startY));
        });
    }

    private void drawEllipse() {
        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();
            double leftX = Math.min(startX, endX);
            double topY = Math.min(startY, endY);
            Shape ellipse = new Shape(leftX, topY, Math.abs(endX - startX), Math.abs(endY - startY),
                Tool.ELLIPSE, lineWidthSlider.getValue(), colorPicker.getValue().toString());
            shapes.addShape(ellipse);
            gc.strokeOval(leftX, topY, Math.abs(endX - startX), Math.abs(endY - startY));
        });
    }

    private void selectShape() {
        canvas.setOnMousePressed(e -> {
            double x = e.getX();
            double y = e.getY();
            boolean isSelect = false;
            for (Shape shape : shapes.getShapes()) {
                if (shape.getTool() == Tool.LINE) {
                    double x1 = shape.getStartX();
                    double y1 = shape.getStartY();
                    double x2 = shape.getEndX();
                    double y2 = shape.getEndY();
                    double distance =
                        Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) / Math.sqrt(
                            Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
                    if (distance <= shape.getLineWidth()) {
                        selectedShapes.add(shape);
                        isSelect = true;
                    }
                } else if (shape.getTool() == Tool.RECTANGLE) {
                    if (x >= shape.getStartX() && x <= shape.getStartX() + shape.getEndX()
                        && y >= shape.getStartY() && y <= shape.getStartY() + shape.getEndY()) {
                        selectedShapes.add(shape);
                        isSelect = true;
                    }
                } else if (shape.getTool() == Tool.ELLIPSE) {
                    double centerX = shape.getStartX() + shape.getEndX() / 2;
                    double centerY = shape.getStartY() + shape.getEndY() / 2;
                    double a = shape.getEndX() / 2;
                    double b = shape.getEndY() / 2;
                    double distance = Math.pow(x - centerX, 2) / Math.pow(a, 2)
                        + Math.pow(y - centerY, 2) / Math.pow(b, 2);
                    if (distance <= 1) {
                        selectedShapes.add(shape);
                        isSelect = true;
                    }
                }
            }
            if (!isSelect) {
                selectedShapes.clear();
            }
            setSelectedShapesLabel();
        });
    }

    private void setSelectedShapesLabel() {
        selectedShapesLabel.setText("Selected: " + selectedShapes.size());
    }

    public void deleteSelectedShapes() {
        for (Shape shape : selectedShapes) {
            shapes.getShapes().remove(shape);
        }
        selectedShapes.clear();
        setSelectedShapesLabel();
        updateCanvas();
    }

    public void clearCanvas() {
        shapes.clearShapes();
        updateCanvas();
    }

}