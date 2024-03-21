package com.example.ooprogproject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The type Balls.
 */
public class Balls {
    private int diameter;
    private Circle balls;
    private Color color;
    private boolean pause = false;

    /**
     * Instantiates a new Balls.
     *
     * @param diameter the diameter
     * @param color    the color
     */
    public Balls(int diameter, Color color) {
        this.diameter = diameter;
        this.balls = new Circle(diameter);
        this.color = color;
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


    /**
     * Sets diameter.
     *
     * @param diameter the diameter
     * @return the diameter
     */
    public int setDiameter(int diameter) {
        this.diameter = diameter;
        return diameter;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public int getRadius() {
        return diameter / 2;
    }

    void pause(double deltaX, double deltaY) {
        double tempX = deltaX;
        double tempY = deltaY;
        if (!pause) {
            pause = true;
            System.out.println("pause");
            deltaX = 0;
            deltaY = 0;


        } else {
            pause = false;
            System.out.println("unpause");
            deltaX = tempX;
            deltaY = tempY;

        }
    }
}