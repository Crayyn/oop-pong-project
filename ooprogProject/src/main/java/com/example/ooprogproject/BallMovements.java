package com.example.ooprogproject;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicInteger;


public class BallMovements {



    public void moveBall(Scene scene, Balls ball, Paddle paddle1, Paddle paddle2, AtomicInteger player1score,
                         AtomicInteger player2score, int scoreToWin, Label player1scoreDisplay,
                         Label player2scoreDisplay, Label player1scores, Label player2scores, Label player1wins,
                         Label player2wins) {
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

                if (newXspeed <= 0) {
                    player2score.getAndIncrement();
                    System.out.println("Player 2 Score: " + player2score);
                    ball.getBalls().setCenterX(scene.getWidth()/2 - ball.getBalls().getRadius());
                    ball.getBalls().setCenterY(scene.getHeight()/2 - ball.getBalls().getRadius());
                    if(player2score.get() == scoreToWin) {
                        player2wins.setTextFill(Color.WHITE);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Runtime.getRuntime().exit(0);
                    }
                    player2scores.setTextFill(Color.WHITE);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    player2scores.setTextFill(Color.TRANSPARENT);
                }

                if(newXspeed >= scene.getWidth()-ball.getBalls().getRadius()) {
                    player1score.getAndIncrement();
                    System.out.println("Player 1 Score: " + player1score);
                    ball.getBalls().setCenterX(scene.getWidth()/2 - ball.getBalls().getRadius());
                    ball.getBalls().setCenterY(scene.getHeight()/2 - ball.getBalls().getRadius());
                    if(player1score.get() == scoreToWin) {
                        player1wins.setTextFill(Color.WHITE);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Runtime.getRuntime().exit(0);
                    }
                    player1scores.setTextFill(Color.WHITE);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    player1scores.setTextFill(Color.TRANSPARENT);
                }



                Platform.runLater(() -> {
                    ball.getBalls().setCenterY(newYspeed);
                    player2scoreDisplay.setText("" + player2score);
                    player1scoreDisplay.setText("" + player1score);

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