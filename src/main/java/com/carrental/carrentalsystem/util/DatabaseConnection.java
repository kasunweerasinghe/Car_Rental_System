package com.carrental.carrentalsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        // Check if we are running tests
        if (System.getProperty("test.env") != null && System.getProperty("test.env").equals("true")) {
            URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
            USER = "root";
            PASSWORD = "Kasun2023..";
        } else {
            URL = "jdbc:mysql://localhost:3306/CarRentalDB?serverTimezone=UTC";
            USER = "root";
            PASSWORD = "Kasun2023..";
        }
    }

    // Singleton instance
    private static DatabaseConnection instance;
    private Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnection() throws SQLException {
        try {
            if (System.getProperty("test.env") != null && System.getProperty("test.env").equals("true")) {
                Class.forName("org.h2.Driver");
            } else {
                Class.forName("com.mysql.cj.jdbc.Driver");
            }
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }

    // Global access point to the Singleton instance
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Method to get the database connection
    public Connection getConnection() {
        return connection;
    }
}