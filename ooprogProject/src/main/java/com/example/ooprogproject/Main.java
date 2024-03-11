package com.example.ooprogproject;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;


public class Main extends Application {

    public static double width = 1280, height = 720;
    public Stage stage;



    @Override
   public void start(Stage stage){
        this.stage = stage;
        Image icon = new Image("file:src/ryanair.png");
        stage.getIcons().add(icon);
        Text title = new Text("Generic Game of Pong");
        //names
        Text player1text = new Text("Player 1 Name: ___");
        TextField player1Name = new TextField("Player 1");
        Text player2text = new Text("Player 2 Name: ___");
        TextField player2Name = new TextField("Player 2");
        //ball speed
        Text speedText = new Text("Select The Ball Speed: ___");
        Spinner<Integer> ballSpeed = new Spinner<Integer>(1, 100, 1);
        Text frequencyText = new Text("How Often Should The Ball Speed Increase: ");
        Spinner<Integer> frequency = new Spinner<Integer>(1, 10, 5);
        //paddles
        Text paddleSize = new Text("Set Size of The Paddles: ");
        Text widthLabel = new Text("Width: ___");
        Text heightLabel = new Text("Height: ___");
        Spinner<Integer> paddleWidth = new Spinner<Integer>(10, 50, 20 );
        Spinner<Integer> paddleHeight = new Spinner<Integer>(10, 500, 100 );
        //score
        Text scoreText = new Text("First to ___ Wins: ");
        Spinner<Integer> scoreToWin = new Spinner<Integer>(1, 20, 5);
        //quiting and buttons
        Text quittingText = new Text("You can press escape to leave at any time!");
        Button setParameters = new Button("Set Parameters");
        Button startButton = new Button("START!");


        setParameters.setOnAction(event -> {
            player1text.setText("Player 1 Name: " + player1Name.getText());
            player2text.setText("Player 2 Name: " + player2Name.getText());
            speedText.setText("Select The Ball Speed: " + ballSpeed.getValue());
            frequencyText.setText("How Often Should The Ball Speed Increase: Every " + frequency.getValue() +" Hits");
            widthLabel.setText("Width: " + paddleWidth.getValue());
            heightLabel.setText("Height: " + paddleHeight.getValue());
            scoreText.setText("First to " + scoreToWin.getValue() + " Wins: ");

        });

        startButton.setOnAction(event -> {
            game( player1Name.getText(), player2Name.getText(), paddleWidth.getValue(), paddleHeight.getValue(), scoreToWin.getValue());
        });

        VBox playerNames = new VBox(20, title, player1text, player1Name, player2text, player2Name, speedText,
                 ballSpeed, frequencyText, frequency, paddleSize);
        playerNames.setAlignment(Pos.TOP_CENTER);
        playerNames.setMaxWidth(width/2);

        HBox paddles = new HBox(20, widthLabel, paddleWidth, heightLabel, paddleHeight);
        paddles.setAlignment(Pos.TOP_CENTER);
        paddles.setMaxWidth(width/2);

        VBox scoresNbuttons = new VBox(20, scoreText, scoreToWin, setParameters, quittingText, startButton);
        scoresNbuttons.setAlignment(Pos.TOP_CENTER);
        scoresNbuttons.setMaxWidth(width/2);

        VBox root = new VBox(20, playerNames, paddles, scoresNbuttons);
        root.setAlignment(Pos.TOP_CENTER);

        Scene menu = new Scene(root, width, height);


        menu.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ESCAPE) stage.close();
        });

        stage.setScene(menu);
        stage.show();
    }

    public void game(String player1Name, String player2Name, int paddleWidth, int paddleHeight, int scoreToWin){

        Balls ball = new Balls(20, Color.RED);
        Paddle paddle1 = new Paddle(paddleHeight, paddleWidth, Color.ORANGE);
        Paddle paddle2 = new Paddle(paddleHeight, paddleWidth, Color.GOLD);
        stage.setTitle("Pong!");
        Label player1 = new Label(player1Name);
        player1.setTextFill(Color.WHITE);
        Label player2 = new Label(player2Name);
        player2.setTextFill(Color.WHITE);
        AtomicInteger player1score = new AtomicInteger();
        AtomicInteger player2score = new AtomicInteger();
        Label player1scoreDisplay = new Label("" + player1score);
        Label player2scoreDisplay = new Label("" + player2score);
        Label player1scores = new Label("Player 1 Scores!");
        Label player2scores = new Label("Player 2 Scores!");
        Label player1wins = new Label("Player 1 Wins!");
        Label player2wins = new Label("Player 2 Wins!");
        player1scoreDisplay.setTextFill(Color.WHITE);
        player2scoreDisplay.setTextFill(Color.WHITE);
        player1scores.setTextFill(Color.TRANSPARENT);
        player2scores.setTextFill(Color.TRANSPARENT);
        player1wins.setTextFill(Color.TRANSPARENT);
        player2wins.setTextFill(Color.TRANSPARENT);


        Pane root = new Pane();
        root.getChildren().addAll(ball.getBalls(), paddle1.getPaddles(), paddle2.getPaddles(), player1, player2,
                player1scoreDisplay, player2scoreDisplay, player1scores, player2scores, player1wins, player2wins);
        root.setStyle("-fx-background-color: Black");

        ChangeListener <Number> layout = ((observableValue, oldValue, newValue) -> {
            width = stage.getWidth();
            height = stage.getHeight();
            Rectangle pddl1 = paddle1.getPaddles();
            Rectangle pddl2 = paddle2.getPaddles();
            Circle bll = ball.getBalls();
            pddl1.setX(20);
            pddl1.setY(height/2-pddl1.getHeight()/2);
            pddl2.setX(width-40-pddl2.getWidth());
            pddl2.setY(height/2-pddl2.getHeight()/2);
            bll.setCenterX(width/2-10);
            bll.setCenterY(height/2);
            player1.setMinWidth(width);
            player1.setMinHeight(50);
            player1.setAlignment(Pos.TOP_LEFT);
            player1.setPadding(new Insets(20, 20, 20, 20));
            player2.setMinWidth(width);
            player2.setMinHeight(50);
            player2.setAlignment(Pos.TOP_RIGHT);
            player2.setPadding(new Insets(20, 20, 20, 20));
            player2.setTextAlignment(TextAlignment.RIGHT);
            player1scoreDisplay.setPadding(new Insets(20, 0, 0, width/2-65));
            player2scoreDisplay.setPadding(new Insets(20, 0, 0, width/2+30));
            player2scores.setPadding(new Insets(height/2, 0, 0, width/2-50));
            player1scores.setPadding(new Insets(height/2, 0, 0, width/2-50));
            player1wins.setPadding(new Insets(height/2, 0, 0, width/2-50));
            player2wins.setPadding(new Insets(height/2, 0, 0, width/2-50));
        });

        stage.widthProperty().addListener(layout);
        stage.heightProperty().addListener(layout);

        Scene scene = new Scene(root, width, height);



        Rectangle pddl1 = paddle1.getPaddles();
        Rectangle pddl2 = paddle2.getPaddles();
        Circle bll = ball.getBalls();
        pddl1.setX(20);
        pddl1.setY(height/2-pddl1.getHeight()/2);
        pddl2.setX(width-20-pddl2.getWidth());
        pddl2.setY(height/2-pddl2.getHeight()/2);
        bll.setCenterX(width/2-10);
        bll.setCenterY(height/2);
        player1.setMinWidth(width);
        player1.setMinHeight(50);
        player1.setAlignment(Pos.TOP_LEFT);
        player1.setPadding(new Insets(20, 20, 20, 20));
        player2.setMinWidth(width);
        player2.setMinHeight(50);
        player2.setAlignment(Pos.TOP_RIGHT);
        player2.setPadding(new Insets(20, 20, 20, 20));
        player1scoreDisplay.setPadding(new Insets(20, 0, 0, width/2-65));
        player2scoreDisplay.setPadding(new Insets(20, 0, 0, width/2+40));
        player2scores.setPadding(new Insets(height/2, 0, 0, width/2-50));
        player1scores.setPadding(new Insets(height/2, 0, 0, width/2-50));
        player1wins.setPadding(new Insets(height/2, 0, 0, width/2-50));
        player2wins.setPadding(new Insets(height/2, 0, 0, width/2-50));

        stage.setScene(scene);
        stage.show();




        BallMovements ballMovements = new BallMovements();
        ballMovements.moveBall(scene, ball, paddle1, paddle2, player1score, player2score, scoreToWin,
                player1scoreDisplay, player2scoreDisplay, player1scores, player2scores, player1wins, player2wins);
        PaddleMovements paddleMovements = new PaddleMovements();
        paddleMovements.movePaddle(scene, paddle1, paddle2);




    }





    public static void main(String[] args) {
        launch();
    }
}