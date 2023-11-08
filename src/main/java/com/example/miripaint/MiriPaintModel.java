package com.example.miripaint;

import java.util.ArrayList;

public class MiriPaintModel {
    private ArrayList<Shape> shapes = new ArrayList<>();

    public void addShape(Shape shape){
        shapes.add(shape);
    }

    public ArrayList<Shape> getShapes(){
        return shapes;
    }

    public void clearShapes(){
        shapes.clear();
    }
}
