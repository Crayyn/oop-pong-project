package com.example.ooprogproject;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PaddleMovements implements EventHandler<KeyEvent> {

    boolean paddle1Up = false;
    boolean paddle1Down = false;
    boolean paddle2Up = false;
    boolean paddle2Down = false;

    public double movePaddle1 = 0;
    public double movePaddle2 = 0;
    private final int speed = 2;

    public void movePaddle(Scene scene, Paddle paddle1, Paddle paddle2) {
        Thread paddleMovement = new Thread(() -> { //thread

            scene.setOnKeyPressed(keyEvent -> {

                if (keyEvent.getCode() == KeyCode.W) {
                    paddle1Up = true;
                } else if (keyEvent.getCode() == KeyCode.S) {
                    paddle1Down = true;
                } else if (keyEvent.getCode() == KeyCode.UP) {
                    paddle2Up = true;
                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                    paddle2Down = true;
                } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    Runtime.getRuntime().exit(0);
                }
            });

            scene.setOnKeyReleased(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.W) {
                    paddle1Up = false;
                } else if (keyEvent.getCode() == KeyCode.S) {
                    paddle1Down = false;
                } else if (keyEvent.getCode() == KeyCode.UP) {
                    paddle2Up = false;
                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                    paddle2Down = false;
                }
                movePaddle1 = 0;
                movePaddle2 = 0;
            });

            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {


                    if (paddle1Up) {
                        movePaddle1 = -speed;
                    } else if (paddle1Down) {
                        movePaddle1 = speed;
                    }
                    if (paddle2Up) {
                        movePaddle2 = -speed;
                    } else if (paddle2Down) {
                        movePaddle2 = speed;
                    }
                }
            };
            timer.start();

            while(true) {



                Platform.runLater(() -> {

                    paddle1.getPaddles().setY(paddle1.getPaddles().getY() + movePaddle1);
                    paddle2.getPaddles().setY(paddle2.getPaddles().getY() + movePaddle2);
                    if (paddle1.getPaddles().getY() < 0) {
                        paddle1.getPaddles().setY(0);
                    }
                    if (paddle1.getPaddles().getY() > scene.getHeight() - paddle1.getPaddles().getHeight()) {
                        paddle1.getPaddles().setY(scene.getHeight() - paddle1.getPaddles().getHeight());
                    }
                    if (paddle2.getPaddles().getY() < 0) {
                        paddle2.getPaddles().setY(0);
                    }
                    if (paddle2.getPaddles().getY() > scene.getHeight() - paddle2.getPaddles().getHeight()) {
                        paddle2.getPaddles().setY(scene.getHeight() - paddle2.getPaddles().getHeight());
                    }
                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        paddleMovement.start();
    }

    @Override
    public void handle(KeyEvent event) {
        // Handle key events here
    }


}