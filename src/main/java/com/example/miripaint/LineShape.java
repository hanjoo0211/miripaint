package com.example.miripaint;

import javafx.scene.canvas.GraphicsContext;

public class LineShape extends Shape {

    public LineShape(double startX, double startY, double endX, double endY, Tool tool,
        double lineWidth, String color) {
        super(startX, startY, endX, endY, tool, lineWidth, color);
    }

    public void draw(GraphicsContext gc) {
        gc.strokeLine(startX, startY, endX, endY);
    }
}
