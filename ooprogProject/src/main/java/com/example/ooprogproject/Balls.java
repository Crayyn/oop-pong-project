package com.example.ooprogproject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The type Balls.
 */
public class Balls {
    private Circle balls;

    /**
     * Instantiates a new Balls.
     *
     * @param diameter the diameter
     * @param color    the color
     */
    public Balls(int diameter, Color color) {
        this.balls = new Circle(diameter);
        this.balls.setFill(color);
    }

    /**
     * Gets balls.
     *
     * @return the balls
     */
    public Circle getBalls() {
        return balls;
    }



}