package com.example.miripaint;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class SelectState implements ToolState {

    private static SelectState selectState;

    private SelectState() {
    }

    public static SelectState getInstance() {
        if (selectState == null) {
            selectState = new SelectState();
        }
        return selectState;
    }

    @Override
    public void toolAction(double startX, double startY, double endX, double endY, Tool tool,
        double lineWidth, String color, GraphicsContext gc, ArrayList<Shape> shapes,
        Shape selectedShape, ArrayList<Shape> selectedShapes) {
        double x = endX;
        double y = endY;
        selectedShape = null;
        for (Shape shape : shapes) {
            if (shape.getTool() == Tool.LINE) {
                double x1 = shape.getStartX();
                double y1 = shape.getStartY();
                double x2 = shape.getEndX();
                double y2 = shape.getEndY();
                double distance =
                    Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) / Math.sqrt(
                        Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
                if (distance <= shape.getLineWidth()) {
                    selectedShape = shape;
                }
            } else if (shape.getTool() == Tool.RECTANGLE) {
                if (x >= shape.getStartX() && x <= shape.getStartX() + shape.getEndX()
                    && y >= shape.getStartY() && y <= shape.getStartY() + shape.getEndY()) {
                    selectedShape = shape;
                }
            } else if (shape.getTool() == Tool.ELLIPSE) {
                double centerX = shape.getStartX() + shape.getEndX() / 2;
                double centerY = shape.getStartY() + shape.getEndY() / 2;
                double a = shape.getEndX() / 2;
                double b = shape.getEndY() / 2;
                double distance = Math.pow(x - centerX, 2) / Math.pow(a, 2)
                    + Math.pow(y - centerY, 2) / Math.pow(b, 2);
                if (distance <= 1) {
                    selectedShape = shape;
                }
            }
        }
        if (selectedShape == null) {
            selectedShapes.clear();
        } else if (!selectedShapes.contains(selectedShape)) {
            selectedShapes.add(selectedShape);
        }
    }
}
