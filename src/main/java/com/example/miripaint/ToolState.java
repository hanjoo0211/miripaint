package com.example.miripaint;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public interface ToolState {

    public void toolAction(double startX, double startY, double endX, double endY, Tool tool,
        double lineWidth, String color, GraphicsContext gc, ArrayList<Shape> shapes,
        ArrayList<Shape> selectedShapes);
}
