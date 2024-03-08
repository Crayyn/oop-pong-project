package com.example.ooprogproject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Paddle {
    private int height;
    private int width;
    private Rectangle paddles;
    private Color color;

    public Paddle(int height, int width, Color color){
        this.height = height;
        this.width = width;
        this.color = color;
        this.paddles = new Rectangle(width, height);
        this.paddles.setFill(color);
    }

    public Rectangle getPaddles(){
        return paddles;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int setHeight(int height){
        this.height = height;
        return height;
    }

    public int setWidth(int width){
        this.width = width;
        return width;
    }



}
