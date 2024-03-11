package com.example.ooprogproject;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class PaddleMovements {




    private int speed = 2;

    public void movePaddle(Scene scene, Paddle paddle1, Paddle paddle2) {
        Thread paddleMovement = new Thread(() -> { //thread

            paddle1.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent);
            paddle2.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent);

            private EventHandler<KeyEvent> keyEvent = new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyCode key = evt.getCode();
                    System.out.println("Key Pressed: " + key);  // for testing

                    switch (key) {
                        case W:
                            paddle1.getPaddles().setY(paddle1.getPaddles().getY() - speed);
                            break;
                        case S:
                            paddle1.getPaddles().setY(paddle1.getPaddles().getY() + speed);
                            break;
                        case UP:
                            paddle2.getPaddles().setY(paddle2.getPaddles().getY() - speed);
                            break;
                        case DOWN:
                            paddle2.getPaddles().setY(paddle2.getPaddles().getY() + speed);
                            break;
                    }
                }
            };

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        paddleMovement.start();
    }




}