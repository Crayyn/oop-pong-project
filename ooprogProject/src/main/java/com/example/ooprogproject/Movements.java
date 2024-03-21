package com.example.ooprogproject;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;


/**
 * The type Movements.
 */
public class Movements implements EventHandler<KeyEvent> {
    private final Balls ball;
    private final Paddle paddle1;
    private final Paddle paddle2;
    private int player1score;
    private int player2score;
    private final Label player1scoreDisplay;
    private final Label player2scoreDisplay;
    private final Label player1scores;
    private final Label player2scores;
    private final Label player1wins;
    private final Label player2wins;
    private final Label pausedText;

    private double deltaX = 0;
    private double deltaY = 0;
    private double memoryX = 0;
    private double memoryY = 0;
    private final int ballSpeed;
    private final int frequency;
    private final int scoreToWin;

    private boolean paddle1Up = false;
    private boolean paddle1Down = false;
    private boolean paddle2Up = false;
    private boolean paddle2Down = false;

    private double movePaddle1 = 0;
    private double movePaddle2 = 0;
    private double paddleSpeed = 0;
    private double memoryPaddleSpeed = 0;

    private boolean pause = false;

    /**
     * Instantiates a new Movements.
     *
     * @param ball                the ball
     * @param paddle1             the paddle 1
     * @param paddle2             the paddle 2
     * @param player1score        the player 1 score
     * @param player2score        the player 2 score
     * @param ballSpeed           the ball speed
     * @param frequency           the frequency
     * @param scoreToWin          the score to win
     * @param player1scoreDisplay the player 1 score display
     * @param player2scoreDisplay the player 2 score display
     * @param player1scores       the player 1 scores
     * @param player2scores       the player 2 scores
     * @param player1wins         the player 1 wins
     * @param player2wins         the player 2 wins
     * @param pausedText          the paused text
     */
    public Movements(Balls ball, Paddle paddle1, Paddle paddle2, int player1score, int player2score,
                int ballSpeed, int frequency, int scoreToWin, Label player1scoreDisplay, Label player2scoreDisplay,
                Label player1scores, Label player2scores, Label player1wins, Label player2wins, Label pausedText) {
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
        this.pausedText = pausedText;

    }

    /**
     * Start game.
     *
     * @param scene the scene
     */
    public void startGame(Scene scene) {
        Thread ballThread = new Thread(() -> moveBall(scene));
        Thread paddleThread = new Thread(() -> movePaddle(scene));

        ballThread.start();
        paddleThread.start();
    }
    /**
     * Moves the ball within the scene, handling collisions with walls and paddles. Updates scores and speeds accordingly.
     *
     * @param  scene    the scene in which the ball is moving
     */
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

            if(checkCollision(newXspeed, newYspeed, ball, paddle1, paddle2)) {
                deltaX = -deltaX;
                memoryX = deltaX;
                noOfHits++;
            }

            if (newXspeed <= 0) {
                player2score++;
                deltaY = ballSpeed;
                deltaX = ballSpeed;
                memoryX = deltaX;
                memoryY = deltaY;
                paddleSpeed = ballSpeed;
                memoryPaddleSpeed = paddleSpeed;
                noOfHits = 0;
                System.out.println("Player 2 Score: " + player2score);
                ball.getBalls().setCenterX(scene.getWidth()/2 - ball.getBalls().getRadius());
                ball.getBalls().setCenterY(scene.getHeight()/2 - ball.getBalls().getRadius());
                if(player2score == scoreToWin) {
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
                player1score++;
                deltaY = ballSpeed;
                deltaX = ballSpeed;
                memoryX = deltaX;
                memoryY = deltaY;
                paddleSpeed = ballSpeed;
                memoryPaddleSpeed = paddleSpeed;
                noOfHits = 0;
                System.out.println("Player 1 Score: " + player1score);
                ball.getBalls().setCenterX(scene.getWidth()/2 - ball.getBalls().getRadius());
                ball.getBalls().setCenterY(scene.getHeight()/2 - ball.getBalls().getRadius());
                if(player1score == scoreToWin) {
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
                } else{
                    deltaY += 0.2;
                }
                if (deltaX < 0){
                    deltaX -= 0.2;
                } else{
                    deltaX += 0.2;
                }
                memoryX = deltaX;
                memoryY = deltaY;
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

    /**
     * Moves the paddle in response to key presses and updates the paddle's position on the scene.
     *
     * @param  scene  the JavaFX scene on which the paddle is moved
     */
    private void movePaddle(Scene scene) {
        paddleSpeed = ballSpeed;
        memoryPaddleSpeed = paddleSpeed;
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
            } else if (keyEvent.getCode() == KeyCode.R) {
                restart(scene);



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

    public static boolean checkCollision(double newXspeed, double newYspeed, Balls ball, Paddle paddle1, Paddle paddle2) {

        return newXspeed <= paddle1.getPaddles().getX() + paddle1.getPaddles().getWidth() +
                ball.getBalls().getRadius() && newYspeed >= paddle1.getPaddles().getY() &&
                newYspeed <= paddle1.getPaddles().getY() + paddle1.getPaddles().getHeight()
                || newXspeed >= paddle2.getPaddles().getX() - ball.getBalls().getRadius() &&
                newXspeed <= paddle2.getPaddles().getX() + paddle2.getPaddles().getWidth() &&
                newYspeed >= paddle2.getPaddles().getY() &&
                newYspeed <= paddle2.getPaddles().getY() + paddle2.getPaddles().getHeight();
    }


    /**
     * Pauses or unpauses the game by toggling the pause boolean and adjusting the game state accordingly.
     * If the game is not currently paused, the pause boolean is set to true and the game state is updated to reflect a paused state.
     * If the game is currently paused, the pause boolean is set to false and the game state is updated to reflect an unpaused state.
     */
    private void pause() {

        if (!pause) {
            pause = true;
            System.out.println("pause");
            deltaX = 0;
            deltaY = 0;
            paddleSpeed = 0;
            pausedText.setTextFill(Color.WHITE);
        } else {
            pause = false;
            System.out.println("unpause");
            deltaX = memoryX;
            deltaY = memoryY;
            paddleSpeed = memoryPaddleSpeed;
            pausedText.setTextFill(Color.TRANSPARENT);
        }

    }

    /**
     * A method to reset the game state including scores, positions, and speeds.
     *
     * @param  scene    the scene where the game is taking place
     */
    private void restart(Scene scene) {
        player1score = 0;
        player2score = 0;
        player1scoreDisplay.setText("" + player1score);
        player2scoreDisplay.setText("" + player2score);

        paddle1.getPaddles().setY(scene.getHeight() / 2 - paddle1.getPaddles().getHeight() / 2);
        paddle2.getPaddles().setY(scene.getHeight() / 2 - paddle2.getPaddles().getHeight() / 2);
        ball.getBalls().setCenterY(scene.getHeight() / 2 - ball.getBalls().getRadius() / 2);
        ball.getBalls().setCenterX(scene.getWidth() / 2 - ball.getBalls().getRadius() / 2);

        pause = false;
        deltaX = ballSpeed;
        deltaY = ballSpeed;
        paddleSpeed = ballSpeed;
        memoryX = deltaX;
        memoryY = deltaY;
        memoryPaddleSpeed = paddleSpeed;

    }



    /**
     * A description of the entire Java function.
     *
     * @param  event	description of parameter
     *
     */

    @Override
    public void handle(KeyEvent event) {
        // Handle key events here
    }
}
