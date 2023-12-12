package com.example.miripaint;

import javafx.scene.canvas.GraphicsContext;

public class EllipseShape extends Shape {

    public EllipseShape(double startX, double startY, double endX, double endY, Tool tool,
        double lineWidth, String color) {
        super(startX, startY, endX, endY, tool, lineWidth, color);
    }

    public void draw(GraphicsContext gc) {
        gc.strokeOval(startX, startY, endX, endY);
    }
}
