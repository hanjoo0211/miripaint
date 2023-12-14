package com.example.miripaint;

import java.net.URL;
import java.util.ArrayList;
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
    private TextField selectedShapeText;
    @FXML
    private TextField selectedShapeStartX;
    @FXML
    private TextField selectedShapeStartY;
    @FXML
    private TextField selectedShapeEndX;
    @FXML
    private TextField selectedShapeEndY;
    @FXML
    private TextField selectedShapeZOrder;
    @FXML
    private Slider lineWidthSlider;
    @FXML
    private ColorPicker colorPicker;
    private GraphicsContext gc;
    private ArrayList<Shape> shapes = new ArrayList<>();
    private ArrayList<Shape> selectedShapes = new ArrayList<>();
    private Tool tool = Tool.LINE;
    private ToolState toolState = LineState.getInstance();
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
        for (Shape shape : shapes) {
            gc.setLineWidth(shape.getLineWidth());
            gc.setStroke(Color.valueOf(shape.getColor()));
            shape.draw(gc);
        }
    }

    public void setTool(ActionEvent event) {
        Button button = (Button) event.getSource();
        String nowTool = button.getUserData().toString();
        toolLabel.setText(nowTool);
        tool = Tool.valueOf(nowTool);
        switch (tool) {
            case LINE:
                toolState = LineState.getInstance();
                break;
            case RECTANGLE:
                toolState = RectangleState.getInstance();
                break;
            case ELLIPSE:
                toolState = EllipseState.getInstance();
                break;
            case SELECT:
                toolState = SelectState.getInstance();
                break;
        }
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
        setLineWidth();
        setColor();
        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();
            toolState.toolAction(startX, startY, endX, endY, tool,
                lineWidthSlider.getValue(),
                colorPicker.getValue().toString(), gc, shapes, selectedShapes);
            setSelectedShapesLabel();
        });
    }

    private void disableTool() {
        canvas.setOnMousePressed(null);
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseReleased(null);
    }

    private void setSelectedShapesLabel() {
        selectedShapesLabel.setText("Selected: " + selectedShapes.size());
        if (selectedShapes.isEmpty()) {
            selectedShapeText.setText("");
            selectedShapeStartX.setText("");
            selectedShapeStartY.setText("");
            selectedShapeEndX.setText("");
            selectedShapeEndY.setText("");
            selectedShapeZOrder.setText("");
        } else {
            Shape selectedShape = selectedShapes.get(selectedShapes.size() - 1);
            selectedShapeText.setText(selectedShape.getTool().toString());
            selectedShapeStartX.setText(String.valueOf(selectedShape.getStartX()));
            selectedShapeStartY.setText(String.valueOf(selectedShape.getStartY()));
            selectedShapeEndX.setText(String.valueOf(selectedShape.getEndX()));
            selectedShapeEndY.setText(String.valueOf(selectedShape.getEndY()));
            selectedShapeZOrder.setText(String.valueOf(shapes.indexOf(selectedShape)));
        }
    }

    public void deleteSelectedShapes() {
        for (Shape shape : selectedShapes) {
            shapes.remove(shape);
        }
        selectedShapes.clear();
        setSelectedShapesLabel();
        updateCanvas();
    }

    public void clearCanvas() {
        shapes.clear();
        updateCanvas();
    }


    public void applyShapeChanges() {
        if (!selectedShapes.isEmpty()) {
            Shape selectedShape = selectedShapes.get(selectedShapes.size() - 1);
            selectedShape.setStartX(Double.parseDouble(selectedShapeStartX.getText()));
            selectedShape.setStartY(Double.parseDouble(selectedShapeStartY.getText()));
            selectedShape.setEndX(Double.parseDouble(selectedShapeEndX.getText()));
            selectedShape.setEndY(Double.parseDouble(selectedShapeEndY.getText()));
            shapes.set(Integer.parseInt(selectedShapeZOrder.getText()), selectedShape); // 문제
            updateCanvas();
        }
    }

}