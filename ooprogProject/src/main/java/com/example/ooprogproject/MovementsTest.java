package com.example.ooprogproject;

import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;

public class MovementsTest {


    @Test
    public void testCheckCollision() {
        Paddle paddle1 = new Paddle(100, 20, Color.ORANGE);
        Paddle paddle2 = new Paddle(100, 20, Color.GOLD);
        Balls ball = new Balls(20, Color.RED);

        // Test cases
        assertTrue(Movements.checkCollision(10.0, 20.0, ball, paddle2, paddle1));
        assertFalse(Movements.checkCollision(100.0, 200.0, ball, paddle2, paddle1));

    }
}