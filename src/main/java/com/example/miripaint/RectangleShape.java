package com.example.miripaint;

import javafx.scene.canvas.GraphicsContext;

public class RectangleShape extends Shape {

    public RectangleShape(double startX, double startY, double endX, double endY, Tool tool,
        double lineWidth, String color) {
        super(startX, startY, endX, endY, tool, lineWidth, color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.strokeRect(startX, startY, endX, endY);
    }
}
