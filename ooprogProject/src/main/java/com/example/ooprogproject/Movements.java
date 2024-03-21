package com.example.ooprogproject;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicInteger;

public class Movements implements EventHandler<KeyEvent> {
    private final Balls ball;
    private final Paddle paddle1;
    private final Paddle paddle2;
    private final AtomicInteger player1score;
    private final AtomicInteger player2score;
    private final Label player1scoreDisplay;
    private final Label player2scoreDisplay;
    private final Label player1scores;
    private final Label player2scores;
    private final Label player1wins;
    private final Label player2wins;

    private double deltaX = 0;
    private double deltaY = 0;
    private double memoryX = 0;
    private double memoryY = 0;
    private int ballSpeed;
    private int frequency;
    private int scoreToWin;

    private boolean paddle1Up = false;
    private boolean paddle1Down = false;
    private boolean paddle2Up = false;
    private boolean paddle2Down = false;

    private double movePaddle1 = 0;
    private double movePaddle2 = 0;
    private double paddleSpeed = 2;
    private double memoryPaddleSpeed = 2;

    private boolean pause = false;

    public Movements(Balls ball, Paddle paddle1, Paddle paddle2, AtomicInteger player1score, AtomicInteger player2score,
                int ballSpeed, int frequency, int scoreToWin, Label player1scoreDisplay, Label player2scoreDisplay,
                Label player1scores, Label player2scores, Label player1wins, Label player2wins) {
        this.ball = ball;
        this.paddle1 = paddle1;
        this.paddle2 = paddle2;
        this.player1score = player1score;
        this.player2score = player2score;
        this.ballSpeed = ballSpeed;
        this.frequency = frequency;
        this.scoreToWin = scoreToWin;
        this.player1scoreDisplay = player1scoreDisplay;
        this.player2scoreDisplay = player2scoreDisplay;
        this.player1scores = player1scores;
        this.player2scores = player2scores;
        this.player1wins = player1wins;
        this.player2wins = player2wins;
    }

    public void startGame(Scene scene) {
        Thread ballThread = new Thread(() -> moveBall(scene));
        Thread paddleThread = new Thread(() -> movePaddle(scene));

        ballThread.start();
        paddleThread.start();
    }

    private void moveBall(Scene scene) {
        deltaX = ballSpeed;
        deltaY = ballSpeed;
        memoryX = deltaX;
        memoryY = deltaY;
        int noOfHits = 0;

        while (true) {
            double newXspeed = ball.getBalls().getCenterX() + deltaX;
            double newYspeed = ball.getBalls().getCenterY() + deltaY;
            ball.getBalls().setCenterX(newXspeed);
            ball.getBalls().setCenterY(newYspeed);
            if (newYspeed <= 0 || newYspeed >= scene.getHeight() - ball.getBalls().getRadius()) {
                deltaY = -deltaY;
                memoryY = deltaY;
                noOfHits++;
            }



            if(newXspeed <= paddle1.getPaddles().getX() + paddle1.getPaddles().getWidth() + ball.getBalls().getRadius() &&
                    newYspeed >= paddle1.getPaddles().getY() &&
                    newYspeed <= paddle1.getPaddles().getY() + paddle1.getPaddles().getHeight()
                    || newXspeed >= paddle2.getPaddles().getX() - ball.getBalls().getRadius() &&
                    newXspeed <= paddle2.getPaddles().getX() + paddle2.getPaddles().getWidth() &&
                    newYspeed >= paddle2.getPaddles().getY() &&
                    newYspeed <= paddle2.getPaddles().getY() + paddle2.getPaddles().getHeight()) {
                deltaX = -deltaX;
                memoryX = deltaX;
                noOfHits++;
            }

            if (newXspeed <= 0) {
                player2score.getAndIncrement();
                deltaY = ballSpeed;
                deltaX = ballSpeed;
                noOfHits = 0;
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
                deltaY = ballSpeed;
                deltaX = ballSpeed;
                noOfHits = 0;
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

            if (noOfHits % frequency == 0 && noOfHits != 0) {
                if (deltaY < 0){
                    deltaY -= 0.2;
                    memoryY = deltaY;
                } else{
                    deltaY += 0.2;
                    memoryY = deltaY;
                }

                if (deltaX < 0){
                    deltaX -= 0.2;
                    memoryX = deltaX;
                } else{
                    deltaX += 0.2;
                    memoryX = deltaX;
                }
                paddleSpeed += 0.2;
                memoryPaddleSpeed = paddleSpeed;
                noOfHits = 0;
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

    private void movePaddle(Scene scene) {
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
            } else if (keyEvent.getCode() == KeyCode.P) {

                pause();
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
                    movePaddle1 = -paddleSpeed;
                } else if (paddle1Down) {
                    movePaddle1 = paddleSpeed;
                }
                if (paddle2Up) {
                    movePaddle2 = -paddleSpeed;
                } else if (paddle2Down) {
                    movePaddle2 = paddleSpeed;
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



    }



    private void pause() {

        if (!pause) {
            pause = true;
            System.out.println("pause");
            deltaX = 0;
            deltaY = 0;
            paddleSpeed = 0;
        } else {
            pause = false;
            System.out.println("unpause");
            deltaX = memoryX;
            deltaY = memoryY;
            paddleSpeed = memoryPaddleSpeed;
        }

    }

    @Override
    public void handle(KeyEvent event) {
        // Handle key events here
    }
}
