package com.example.ooprogproject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * The type Paddle.
 */
public class Paddle {
    private Rectangle paddles;

    /**
     * Instantiates a new Paddle.
     *
     * @param height the height
     * @param width  the width
     * @param color  the color
     */
    public Paddle(int height, int width, Color color){
        this.paddles = new Rectangle(width, height);
        this.paddles.setFill(color);
    }

    /**
     * Get paddles rectangle.
     *
     * @return the rectangle
     */
    public Rectangle getPaddles(){
        return paddles;
    }


}
