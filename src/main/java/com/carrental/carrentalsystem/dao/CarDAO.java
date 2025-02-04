/**
 * created by kasun weerasinghe
 * Date: 2/4/25
 * Time: 10:18 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.dao;

import com.carrental.carrentalsystem.model.Car;
import com.carrental.carrentalsystem.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    // Add a new car to the database
    public boolean addCar(Car car) {
        String query = "INSERT INTO Car (brand, model, year, price, isAvailable) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setDouble(4, car.getPrice());
            preparedStatement.setBoolean(5, car.isAvailable());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all cars from the database
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM Car";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car car = new Car();
                car.setCarId(resultSet.getInt("carId"));
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setYear(resultSet.getInt("year"));
                car.setPrice(resultSet.getDouble("price"));
                car.setAvailable(resultSet.getBoolean("isAvailable"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
