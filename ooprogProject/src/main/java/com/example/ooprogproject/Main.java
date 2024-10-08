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
import java.io.*;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The type Main.
 */
public class Main extends Application implements Serializable {

    /**
     * The width.
     */
    public static double width = 1280,
    /**
     * The Height.
     */
    height = 720;
    /**
     * The Stage.
     */
    public Stage stage;

    /**
     * The Player 1 name save.
     */
    String player1NameSave = "Player 1";
    /**
     * The Player 2 name save.
     */
    String player2NameSave = "Player 2";
    /**
     * The Ball speed save.
     */
    int ballSpeedSave = 2;
    /**
     * The Frequency save.
     */
    int frequencySave = 5;
    /**
     * The Paddle width save.
     */
    int paddleWidthSave = 20;
    /**
     * The Paddle height save.
     */
    int paddleHeightSave = 100;
    /**
     * The Score to win save.
     */
    int scoreToWinSave = 5;



    /**
     * The main menu for the game.
     *
     * @param  stage    The JavaFX stage where the game will be displayed
     */
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        this.stage = stage;

        //load save data
        if(new File("saveParams.ser").exists()) {
            FileInputStream fileIn = new FileInputStream("saveParams.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            player1NameSave = (String) in.readObject();
            player2NameSave = (String) in.readObject();
            ballSpeedSave = (int) in.readObject();
            frequencySave = (int) in.readObject();
            paddleWidthSave = (int) in.readObject();
            paddleHeightSave = (int) in.readObject();
            scoreToWinSave = (int) in.readObject();
            in.close();
            fileIn.close();
        }

        // Get the singleton instance of DatabaseConnection
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        try {
            // Get the database connection
            Connection connection = databaseConnection.getConnection();
            System.out.println("Connected to the database");
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Game (
                        game_id INT AUTO_INCREMENT PRIMARY KEY,
                        game_name VARCHAR(255) NOT NULL,
                        player1_name VARCHAR(255) NOT NULL,
                        player2_name VARCHAR(255) NOT NULL,
                        player1_score INT NOT NULL DEFAULT 0,
                        player2_score INT NOT NULL DEFAULT 0,
                        score_to_win INT NOT NULL DEFAULT 5
                    );

                    """);
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //generate menu
        Image icon = new Image("file:ooprogProject/src/ryanair.png");
        stage.getIcons().add(icon);
        Text title = new Text("Generic Game of Pong");
        //names
        Text player1text = new Text("Player 1 Name: ___");
        TextField player1Name = new TextField(player1NameSave);
        Text player2text = new Text("Player 2 Name: ___");
        TextField player2Name = new TextField(player2NameSave);
        //ball speed
        Text speedText = new Text("Select The Ball Speed: ___");
        Spinner<Integer> ballSpeed = new Spinner<>(1, 100, ballSpeedSave);
        Text frequencyText = new Text("How Often Should The Ball Speed Increase: ");
        Spinner<Integer> frequency = new Spinner<>(1, 10, frequencySave);
        //paddles
        Text paddleSize = new Text("Set Size of The Paddles: ");
        Text widthLabel = new Text("Width: ___");
        Text heightLabel = new Text("Height: ___");
        Spinner<Integer> paddleWidth = new Spinner<>(10, 50, paddleWidthSave);
        Spinner<Integer> paddleHeight = new Spinner<>(10, 500, paddleHeightSave);
        //score
        Text scoreText = new Text("First to ___ Wins: ");
        Spinner<Integer> scoreToWin = new Spinner<>(1, 20, scoreToWinSave);
        //info and buttons
        Text infoText = new Text("""
                ~~INSTRUCTIONS~~
                You can press:
                \t-ESC to leave
                \t-P to pause 
                \t-R to restart 
                \t-J to save the current game state to a database
                \t-K to save the current game state to a file
                \t-M to load a saved game state from a database
                \t-L to load a saved game state from a file""");
        Button setParameters = new Button("Save Parameters to File");
        Button startButton = new Button("START!");


        HBox infoBox = new HBox();
        infoBox.setSpacing(20);
        infoBox.setPadding(new Insets(10, 10, 10, 10));
        infoBox.getChildren().addAll(infoText);

        setParameters.setOnAction(event -> {
            player1text.setText("Player 1 Name: " + player1Name.getText());
            player1NameSave = player1Name.getText();
            player2text.setText("Player 2 Name: " + player2Name.getText());
            player2NameSave = player2Name.getText();
            speedText.setText("Select The Ball Speed: " + ballSpeed.getValue());
            ballSpeedSave = ballSpeed.getValue();
            frequencyText.setText("How Often Should The Ball Speed Increase: Every " + frequency.getValue() +" Hits");
            frequencySave = frequency.getValue();
            widthLabel.setText("Width: " + paddleWidth.getValue());
            paddleWidthSave = paddleWidth.getValue();
            heightLabel.setText("Height: " + paddleHeight.getValue());
            paddleHeightSave = paddleHeight.getValue();
            scoreText.setText("First to " + scoreToWin.getValue() + " Wins: ");
            scoreToWinSave = scoreToWin.getValue();

            //save to file
            try {
                FileOutputStream fileOut = new FileOutputStream("saveParams.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(player1NameSave);
                out.writeObject(player2NameSave);
                out.writeObject(ballSpeedSave);
                out.writeObject(frequencySave);
                out.writeObject(paddleWidthSave);
                out.writeObject(paddleHeightSave);
                out.writeObject(scoreToWinSave);
                out.close();
                fileOut.close();
            } catch (IOException i) {
                throw new RuntimeException(i);
            }
            System.out.println("Game Parameters Saved!");
        });

        startButton.setOnAction(event -> game( player1Name.getText(), player2Name.getText(), ballSpeed.getValue(), frequency.getValue(),
                paddleWidth.getValue(), paddleHeight.getValue(), scoreToWin.getValue()));

        VBox infoTextBox = new VBox(10, infoText);
        //infoTextBox.setAlignment(Pos.TOP_LEFT);
        infoTextBox.setMaxWidth(50);

        VBox playerNames = new VBox(20, title, player1text, player1Name, player2text, player2Name, speedText,
                 ballSpeed, frequencyText, frequency, paddleSize);
        playerNames.setAlignment(Pos.TOP_CENTER);
        playerNames.setMaxWidth(width/2);

        HBox paddles = new HBox(20, widthLabel, paddleWidth, heightLabel, paddleHeight);
        paddles.setAlignment(Pos.TOP_CENTER);
        paddles.setMaxWidth(width/2);

        VBox scoresNbuttons = new VBox(20, scoreText, scoreToWin, setParameters, startButton);
        scoresNbuttons.setAlignment(Pos.TOP_CENTER);
        scoresNbuttons.setMaxWidth(width/2);

        VBox root = new VBox(20, playerNames, paddles, scoresNbuttons);
        root.setAlignment(Pos.TOP_CENTER);

        Pane rootPane = new Pane();
        rootPane.getChildren().addAll(infoTextBox, root);
        root.relocate(width/3,  0);
        infoTextBox.relocate(20, height/3);

        Scene menu = new Scene(rootPane, width, height);


        menu.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ESCAPE) stage.close();
        });

        stage.setScene(menu);
        stage.show();
    }

    /**
     * Generate a game with the specified parameters.
     *
     * @param player1Name  the name of player 1
     * @param player2Name  the name of player 2
     * @param ballSpeed    the speed of the ball
     * @param frequency    the frequency of movements
     * @param paddleWidth  the width of the paddle
     * @param paddleHeight the height of the paddle
     * @param scoreToWin   the score needed to win
     */
    public void game(String player1Name, String player2Name, int ballSpeed, int frequency, int paddleWidth, int paddleHeight, int scoreToWin){

        Balls ball = new Balls(20, Color.RED);
        Paddle paddle1 = new Paddle(paddleHeight, paddleWidth, Color.ORANGE);
        Paddle paddle2 = new Paddle(paddleHeight, paddleWidth, Color.GOLD);

        stage.setTitle("Pong!");
        Label player1name = new Label(player1Name);
        player1name.setTextFill(Color.WHITE);
        Label player2name = new Label(player2Name);
        player2name.setTextFill(Color.WHITE);
        int player1score = 0;
        int player2score = 0;
        Label player1scoreDisplay = new Label("" + player1score);
        Label player2scoreDisplay = new Label("" + player2score);
        Label player1scores = new Label(player1Name + " Scores!");
        Label player2scores = new Label(player2Name + " Scores!");
        Label player1wins = new Label(player1Name + " Wins!");
        Label player2wins = new Label(player2Name + " Wins!");
        Label pausedText = new Label("Game Paused!");
         Label savedText = new Label("Game Saved!\nTo resume this game again later press \"L\"");


        player1scoreDisplay.setTextFill(Color.WHITE);
        player2scoreDisplay.setTextFill(Color.WHITE);
        player1scores.setTextFill(Color.TRANSPARENT);
        player2scores.setTextFill(Color.TRANSPARENT);
        player1wins.setTextFill(Color.TRANSPARENT);
        player2wins.setTextFill(Color.TRANSPARENT);
        pausedText.setTextFill(Color.TRANSPARENT);
        savedText.setTextFill(Color.TRANSPARENT);



        Pane root = new Pane();
        root.getChildren().addAll(ball.getBalls(), paddle1.getPaddles(), paddle2.getPaddles(), player1name, player2name,
                player1scoreDisplay, player2scoreDisplay, player1scores, player2scores, player1wins, player2wins,
                pausedText, savedText);
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
            player1name.setMinWidth(width);
            player1name.setMinHeight(50);
            player1name.setAlignment(Pos.TOP_LEFT);
            player1name.setPadding(new Insets(20, 20, 20, 20));
            player2name.setMinWidth(width);
            player2name.setMinHeight(50);
            player2name.setAlignment(Pos.TOP_RIGHT);
            player2name.setPadding(new Insets(20, 20, 20, 20));
            player2name.setTextAlignment(TextAlignment.RIGHT);
            player1scoreDisplay.setPadding(new Insets(20, 0, 0, width/2-65));
            player2scoreDisplay.setPadding(new Insets(20, 0, 0, width/2+30));
            player2scores.setPadding(new Insets(height/2, 0, 0, width/2-50));
            player1scores.setPadding(new Insets(height/2, 0, 0, width/2-50));
            player1wins.setPadding(new Insets(height/2, 0, 0, width/2-50));
            player2wins.setPadding(new Insets(height/2, 0, 0, width/2-50));
            pausedText.setPadding(new Insets(height/2, 0, 0, width/2-50));
            savedText.setPadding(new Insets(height/2, 0, 0, width/2-50));

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
        player1name.setMinWidth(width);
        player1name.setMinHeight(50);
        player1name.setAlignment(Pos.TOP_LEFT);
        player1name.setPadding(new Insets(20, 20, 20, 20));
        player2name.setMinWidth(width);
        player2name.setMinHeight(50);
        player2name.setAlignment(Pos.TOP_RIGHT);
        player2name.setPadding(new Insets(20, 20, 20, 20));
        player1scoreDisplay.setPadding(new Insets(20, 0, 0, width/2-65));
        player2scoreDisplay.setPadding(new Insets(20, 0, 0, width/2+40));
        player2scores.setPadding(new Insets(height/2, 0, 0, width/2-50));
        player1scores.setPadding(new Insets(height/2, 0, 0, width/2-50));
        player1wins.setPadding(new Insets(height/2, 0, 0, width/2-50));
        player2wins.setPadding(new Insets(height/2, 0, 0, width/2-50));
        pausedText.setPadding(new Insets(height/2, 0, 0, width/2-50));
        savedText.setPadding(new Insets(height/2, 0, 0, width/2-50));

        stage.setScene(scene);
        stage.show();


        Movements movements = null;
        try {
            movements = new Movements(ball, paddle1, paddle2, player1score, player2score, ballSpeed,
                    frequency, scoreToWin, player1scoreDisplay, player2scoreDisplay, player1scores, player2scores,
                    player1wins, player2wins, pausedText, savedText, player1name, player2name);
        } catch (MalformedURLException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        movements.startGame(scene);




    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch();
    }
}