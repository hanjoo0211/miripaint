package com.example.miripaint;

import javafx.scene.shape.Path;

public class Shape {
    private double startX, startY, endX, endY;
    private Tool tool;
    private double lineWidth;
    private String color;

    public Shape(double startX, double startY, double endX, double endY, Tool tool, double lineWidth, String color){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.tool = tool;
        this.lineWidth = lineWidth;
        this.color = color;
    }

    public double getStartX(){
        return startX;
    }

    public double getStartY(){
        return startY;
    }

    public double getEndX(){
        return endX;
    }

    public double getEndY(){
        return endY;
    }

    public Tool getTool(){
        return tool;
    }

    public double getLineWidth(){
        return lineWidth;
    }

    public String getColor(){
        return color;
    }

    public void setStartX(double startX){
        this.startX = startX;
    }

    public void setStartY(double startY){
        this.startY = startY;
    }

    public void setEndX(double endX){
        this.endX = endX;
    }

    public void setEndY(double endY){
        this.endY = endY;
    }

}
