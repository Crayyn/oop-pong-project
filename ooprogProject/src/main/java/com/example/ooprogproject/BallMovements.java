package com.example.ooprogproject;
import javafx.scene.Scene;
import javafx.application.Platform;




public class BallMovements {



    public void moveBall(Scene scene, Balls ball, Paddle paddle1, Paddle paddle2) {
        Thread ballMovement = new Thread(() -> { //thread
            double deltaX = 2.0;
            double deltaY = 2.0;
            while (true) {
                double newXspeed = ball.getBalls().getCenterX() + deltaX;
                double newYspeed = ball.getBalls().getCenterY() + deltaY;
                ball.getBalls().setCenterX(newXspeed);
                ball.getBalls().setCenterY(newYspeed);
                if(newYspeed <= 0 || newYspeed >= scene.getHeight()-ball.getBalls().getRadius()) {
                    deltaY = -deltaY;
                }

                if(newXspeed <= paddle1.getPaddles().getX() + paddle1.getPaddles().getWidth() &&
                        newYspeed >= paddle1.getPaddles().getY() &&
                        newYspeed <= paddle1.getPaddles().getY() + paddle1.getPaddles().getHeight()
                        || newXspeed >= paddle2.getPaddles().getX() &&
                        newXspeed <= paddle2.getPaddles().getX() + paddle2.getPaddles().getWidth() &&
                        newYspeed >= paddle2.getPaddles().getY() &&
                        newYspeed <= paddle2.getPaddles().getY() + paddle2.getPaddles().getHeight()) {
                    deltaX = -deltaX;
                }


                Platform.runLater(() -> {
                    ball.getBalls().setCenterY(newYspeed);

                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        );
        ballMovement.start();
    }


}