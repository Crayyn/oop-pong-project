package com.example.ooprogproject;
import javafx.scene.Scene;
import javafx.application.Platform;




public class BallMovements {



    public void moveBall(Scene scene, Balls ball) {
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