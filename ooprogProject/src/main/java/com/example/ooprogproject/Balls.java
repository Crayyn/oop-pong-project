package com.example.ooprogproject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Balls {
    private int diameter;
    private Circle balls;
    private Color color;

    public Balls(int diameter, Color color) {
        this.diameter = diameter;
        this.balls = new Circle(diameter);
        this.color = color;
        this.balls.setFill(color);
    }

    public Circle getBalls() {
        return balls;
    }


    public int setDiameter(int diameter) {
        this.diameter = diameter;
        return diameter;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return diameter / 2;
    }
}