package com.carrental.carrentalsystem.dao;

import com.carrental.carrentalsystem.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    private Connection connection; // Connection will be injected

    public CarDAO() {}
    // Constructor to accept a Connection object
    public CarDAO(Connection connection) {
        this.connection = connection;
    }

    // Add a new car to the database
    public boolean addCar(Car car) {
        String query = "INSERT INTO Car (carId, brand, model, year, price, isAvailable) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, car.getCarId());
            preparedStatement.setString(2, car.getBrand());
            preparedStatement.setString(3, car.getModel());
            preparedStatement.setInt(4, car.getYear());
            preparedStatement.setDouble(5, car.getPrice());
            preparedStatement.setBoolean(6, car.isAvailable());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get individual car from database test with carId
    public Car getCar(String carId) {
        String query = "SELECT * FROM Car WHERE carId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { // If a car is found
                return new Car(
                        resultSet.getString("carId"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("year"),
                        resultSet.getDouble("price"),
                        resultSet.getBoolean("isAvailable")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no car is found or if an exception occurs
    }

    // Get all cars from the database
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM Car";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Car car = new Car();
                car.setCarId(resultSet.getString("carId"));
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

    // Delete a car from the database
    public boolean deleteCar(String carId) {
        String query = "DELETE FROM Car WHERE carId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, carId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update a car in the database
    public boolean updateCar(Car car) {
        String query = "UPDATE Car SET brand = ?, model = ?, year = ?, price = ?, isAvailable = ? WHERE carId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setDouble(4, car.getPrice());
            preparedStatement.setBoolean(5, car.isAvailable());
            preparedStatement.setString(6, car.getCarId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get the total count of cars
    public int getCarCount() {
        String query = "SELECT COUNT(*) FROM Car";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}