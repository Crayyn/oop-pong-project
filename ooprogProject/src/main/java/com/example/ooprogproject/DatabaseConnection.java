package com.example.ooprogproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Singleton instance
    private static DatabaseConnection instance;

    // JDBC URL, username, and password
    private String url = "jdbc:mysql://localhost:3306/pong_database?useSSL=false";
    private String user = "root";
    private String pass = "munster5";

    // Connection object
    private Connection connection;

    // Private constructor to prevent instantiation from outside
    private DatabaseConnection() {
        // Initialize the connection
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    // Static method to get the singleton instance
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Method to get the database connection
    public Connection getConnection() {
        return connection;
    }

    // Method to close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close the database connection", e);
        }
    }
}
