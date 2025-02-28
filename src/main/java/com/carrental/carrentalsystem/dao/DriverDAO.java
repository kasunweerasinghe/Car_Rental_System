/**
 * created by kasun weerasinghe
 * Date: 2/4/25
 * Time: 5:52 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.dao;

import com.carrental.carrentalsystem.model.Car;
import com.carrental.carrentalsystem.model.Driver;
import com.carrental.carrentalsystem.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {

    private Connection connection; // Connection will be injected

    public DriverDAO() {}
    // Constructor to accept a Connection object
    public DriverDAO(Connection connection) {
        this.connection = connection;
    }

    // Add a new driver to the database
    public boolean addDriver(Driver driver) {
        String query = "INSERT INTO Driver (driverId, driverName, driverAddress, driverAge, driverNationalId, isDriverAvailable) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, driver.getDriverId());
                preparedStatement.setString(2, driver.getDriverName());
                preparedStatement.setString(3, driver.getDriverAddress());
                preparedStatement.setInt(4, driver.getDriverAge());
                preparedStatement.setString(5, driver.getDriverNationalId());
                preparedStatement.setBoolean(6, driver.isDriverAvailable());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get individual car from database test with driverId
    public Driver getDriver(String driverId) {
        String query = "SELECT * FROM Driver WHERE driverId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Driver(
                        resultSet.getString("driverId"),
                        resultSet.getString("driverName"),
                        resultSet.getString("driverAddress"),
                        resultSet.getInt("driverAge"),
                        resultSet.getString("driverNationalId"),
                        resultSet.getBoolean("isDriverAvailable")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all drivers from the database
    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM Driver";

        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Driver driver = new Driver();
                    driver.setDriverId(resultSet.getString("driverId"));
                    driver.setDriverName(resultSet.getString("driverName"));
                    driver.setDriverAddress(resultSet.getString("driverAddress"));
                    driver.setDriverAge(resultSet.getInt("driverAge"));
                    driver.setDriverNationalId(resultSet.getString("driverNationalId"));
                    driver.setDriverAvailable(resultSet.getBoolean("isDriverAvailable"));
                    drivers.add(driver);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    // delete cars from the database
    public boolean deleteDriver(String driverId) {
        String query = "DELETE FROM Driver WHERE driverId = ?";

        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, driverId);
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // update Driver
    public boolean updateDriver(Driver driver) {
        String query = "UPDATE Driver SET driverName = ?, driverAddress = ?, driverAge = ?, driverNationalId = ? WHERE driverId = ?";

        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, driver.getDriverName());
                preparedStatement.setString(2, driver.getDriverAddress());
                preparedStatement.setInt(3, driver.getDriverAge());
                preparedStatement.setString(4, driver.getDriverNationalId());
                preparedStatement.setString(5, driver.getDriverId());

                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get the total count of cars
    public int getDriverCount() {
        String query = "SELECT COUNT(*) FROM Driver";

        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
