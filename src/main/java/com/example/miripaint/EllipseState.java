package com.example.miripaint;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class EllipseState implements ToolState {

    private static EllipseState ellipseState;

    private EllipseState() {
    }

    public static EllipseState getInstance() {
        if (ellipseState == null) {
            ellipseState = new EllipseState();
        }
        return ellipseState;
    }

    @Override
    public void toolAction(double startX, double startY, double endX, double endY, Tool tool,
        double lineWidth, String color, GraphicsContext gc, ArrayList<Shape> shapes,
        ArrayList<Shape> selectedShapes) {
        double leftX = Math.min(startX, endX);
        double topY = Math.min(startY, endY);
        Shape shape = new EllipseShape(leftX, topY, Math.abs(endX - startX),
            Math.abs(endY - startY), tool, lineWidth, color);
        shapes.add(shape);
        shape.draw(gc);
    }
}
