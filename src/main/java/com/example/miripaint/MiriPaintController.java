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
    //    private Shape selectedShape = null;
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

//    private void drawLine() {
//        canvas.setOnMousePressed(e -> {
//            startX = e.getX();
//            startY = e.getY();
//        });
//        canvas.setOnMouseReleased(e -> {
//            endX = e.getX();
//            endY = e.getY();
//            LineShape line = new LineShape(startX, startY, endX, endY, Tool.LINE,
//                lineWidthSlider.getValue(), colorPicker.getValue().toString());
//            shapes.add(line);
//            line.draw(gc);
//        });
//    }
//
//    private void drawRectangle() {
//        canvas.setOnMousePressed(e -> {
//            startX = e.getX();
//            startY = e.getY();
//        });
//        canvas.setOnMouseReleased(e -> {
//            endX = e.getX();
//            endY = e.getY();
//            double leftX = Math.min(startX, endX);
//            double topY = Math.min(startY, endY);
//            RectangleShape rectangle = new RectangleShape(leftX, topY, Math.abs(endX - startX),
//                Math.abs(endY - startY), Tool.RECTANGLE, lineWidthSlider.getValue(),
//                colorPicker.getValue().toString());
//            shapes.add(rectangle);
//            rectangle.draw(gc);
//        });
//    }
//
//    private void drawEllipse() {
//        canvas.setOnMousePressed(e -> {
//            startX = e.getX();
//            startY = e.getY();
//        });
//        canvas.setOnMouseReleased(e -> {
//            endX = e.getX();
//            endY = e.getY();
//            double leftX = Math.min(startX, endX);
//            double topY = Math.min(startY, endY);
//            EllipseShape ellipse = new EllipseShape(leftX, topY, Math.abs(endX - startX),
//                Math.abs(endY - startY),
//                Tool.ELLIPSE, lineWidthSlider.getValue(), colorPicker.getValue().toString());
//            shapes.add(ellipse);
//            ellipse.draw(gc);
//        });
//    }
//
//    private void selectShape() {
//        canvas.setOnMousePressed(e -> {
//            double x = e.getX();
//            double y = e.getY();
//            selectedShape = null;
//            for (Shape shape : shapes) {
//                if (shape.getTool() == Tool.LINE) {
//                    double x1 = shape.getStartX();
//                    double y1 = shape.getStartY();
//                    double x2 = shape.getEndX();
//                    double y2 = shape.getEndY();
//                    double distance =
//                        Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) / Math.sqrt(
//                            Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
//                    if (distance <= shape.getLineWidth()) {
//                        selectedShape = shape;
//                    }
//                } else if (shape.getTool() == Tool.RECTANGLE) {
//                    if (x >= shape.getStartX() && x <= shape.getStartX() + shape.getEndX()
//                        && y >= shape.getStartY() && y <= shape.getStartY() + shape.getEndY()) {
//                        selectedShape = shape;
//                    }
//                } else if (shape.getTool() == Tool.ELLIPSE) {
//                    double centerX = shape.getStartX() + shape.getEndX() / 2;
//                    double centerY = shape.getStartY() + shape.getEndY() / 2;
//                    double a = shape.getEndX() / 2;
//                    double b = shape.getEndY() / 2;
//                    double distance = Math.pow(x - centerX, 2) / Math.pow(a, 2)
//                        + Math.pow(y - centerY, 2) / Math.pow(b, 2);
//                    if (distance <= 1) {
//                        selectedShape = shape;
//                    }
//                }
//            }
//            if (selectedShape == null) {
//                selectedShapes.clear();
//            } else if (!selectedShapes.contains(selectedShape)) {
//                selectedShapes.add(selectedShape);
//            }
//            setSelectedShapesLabel();
//        });
//    }

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