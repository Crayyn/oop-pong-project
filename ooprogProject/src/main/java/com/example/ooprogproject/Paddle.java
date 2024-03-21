package com.example.ooprogproject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * The type Paddle.
 */
public class Paddle {
    private int height;
    private int width;
    private Rectangle paddles;
    private Color color;

    /**
     * Instantiates a new Paddle.
     *
     * @param height the height
     * @param width  the width
     * @param color  the color
     */
    public Paddle(int height, int width, Color color){
        this.height = height;
        this.width = width;
        this.color = color;
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

    /**
     * Get height int.
     *
     * @return the int
     */
    public int getHeight(){
        return height;
    }

    /**
     * Get width int.
     *
     * @return the int
     */
    public int getWidth(){
        return width;
    }

    /**
     * Set height int.
     *
     * @param height the height
     * @return the int
     */
    public int setHeight(int height){
        this.height = height;
        return height;
    }

    /**
     * Set width int.
     *
     * @param width the width
     * @return the int
     */
    public int setWidth(int width){
        this.width = width;
        return width;
    }



}
