package com.example.miripaint;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class RectangleState implements ToolState {

    private static RectangleState rectangleState;

    private RectangleState() {
    }

    public static RectangleState getInstance() {
        if (rectangleState == null) {
            rectangleState = new RectangleState();
        }
        return rectangleState;
    }

    @Override
    public void toolAction(double startX, double startY, double endX, double endY, Tool tool,
        double lineWidth, String color, GraphicsContext gc, ArrayList<Shape> shapes,
        ArrayList<Shape> selectedShapes) {
        double leftX = Math.min(startX, endX);
        double topY = Math.min(startY, endY);
        Shape shape = new RectangleShape(leftX, topY, Math.abs(endX - startX),
            Math.abs(endY - startY), tool, lineWidth, color);
        shapes.add(shape);
        shape.draw(gc);
    }
}
