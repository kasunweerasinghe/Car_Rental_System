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

    // Add a new driver to the database
    public static boolean addDriver(Driver driver) {
        String query = "INSERT INTO Driver (driverId, driverName, driverAddress, driverAge, driverNationalId, isDriverAvailable) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, driver.getDriverId());
            preparedStatement.setString(2, driver.getDriverName());
            preparedStatement.setString(3, driver.getDriverAddress());
            preparedStatement.setInt(4, driver.getDriverAge());
            preparedStatement.setString(5, driver.getDriverNationalId());
            preparedStatement.setBoolean(6, driver.isDriverAvailable());
            System.out.println(query);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all drivers from the database
    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM Driver";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }


    // delete cars from the database
    public boolean deleteDriver(String driverId) {
        String query = "DELETE FROM Driver WHERE driverId = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, driverId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
