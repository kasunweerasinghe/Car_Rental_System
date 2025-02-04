/**
 * created by kasun weerasinghe
 * Date: 1/28/25
 * Time: 7:25 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/CarRentalDB?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Kasun2023..";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
}
