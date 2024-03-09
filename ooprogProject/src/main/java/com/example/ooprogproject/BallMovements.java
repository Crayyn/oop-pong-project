package com.example.ooprogproject;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.application.Platform;


public class BallMovements {

    Scene scene;



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
                    newYspeed *= -1;
                    Thread.sleep(200);
                }

                double finalNewYspeed = newYspeed;
                Platform.runLater(() -> {
                    ball.getBalls().setCenterY(finalNewYspeed);

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