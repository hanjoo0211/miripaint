package com.example.miripaint;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class LineState implements ToolState {

    private static LineState lineState;

    private LineState() {
    }

    public static LineState getInstance() {
        if (lineState == null) {
            lineState = new LineState();
        }
        return lineState;
    }

    @Override
    public void toolAction(double startX, double startY, double endX, double endY, Tool tool,
        double lineWidth, String color, GraphicsContext gc, ArrayList<Shape> shapes,
        Shape selectedShape, ArrayList<Shape> selectedShapes) {
        Shape shape = new LineShape(startX, startY, endX, endY, tool, lineWidth, color);
        shapes.add(shape);
        shape.draw(gc);
    }
}
