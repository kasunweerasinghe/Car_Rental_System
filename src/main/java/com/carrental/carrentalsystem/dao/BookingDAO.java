/**
 * created by kasun weerasinghe
 * Date: 2/15/25
 * Time: 10:43 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.dao;

import com.carrental.carrentalsystem.model.Booking;
import com.carrental.carrentalsystem.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // Add a new booking to the database
    public boolean placeBooking(Booking booking) {
        String query = "INSERT INTO Booking (bookId, customerName, date, carBrand, carModel, price, pickupLocation, dropLocation, tripStartDate, tripEndDate, totalPrice, driverName, driverId, driverAge) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, booking.getBookingId());
                preparedStatement.setString(2, booking.getCustomerName());
                preparedStatement.setDate(3, booking.getCurrentDate());
                preparedStatement.setString(4, booking.getCarBrand());
                preparedStatement.setString(5, booking.getCarModel());
                preparedStatement.setInt(6, booking.getPrice());
                preparedStatement.setString(7, booking.getPickupLocation());
                preparedStatement.setString(8, booking.getDropLocation());
                preparedStatement.setDate(9, booking.getStartDate());
                preparedStatement.setDate(10, booking.getEndDate());
                preparedStatement.setInt(11, booking.getTotalPrice());
                preparedStatement.setString(12, booking.getDriverName());
                preparedStatement.setString(13, booking.getDriverId());
                preparedStatement.setInt(14, booking.getDriverAge());

                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all cars from the database
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Booking";

        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Booking booking = new Booking();
                    booking.setBookingId(resultSet.getString("bookId"));
                    booking.setCustomerName(resultSet.getString("customerName"));
                    booking.setCurrentDate(resultSet.getDate("date"));
                    booking.setCarBrand(resultSet.getString("carBrand"));
                    booking.setCarModel(resultSet.getString("carModel"));
                    booking.setPrice(resultSet.getInt("price"));
                    booking.setPickupLocation(resultSet.getString("pickupLocation"));
                    booking.setDropLocation(resultSet.getString("dropLocation"));
                    booking.setStartDate(resultSet.getDate("tripStartDate"));
                    booking.setEndDate(resultSet.getDate("tripEndDate"));
                    booking.setTotalPrice(resultSet.getInt("totalPrice"));
                    booking.setDriverName(resultSet.getString("driverName"));
                    booking.setDriverId(resultSet.getString("driverId"));
                    booking.setDriverAge(resultSet.getInt("driverAge"));
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Update car availability
    public void updateCarAvailability(String carBrand, String carModel, boolean isAvailable) {
        String sql = "UPDATE Car SET isAvailable = ? WHERE brand = ? AND model = ?";

        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setBoolean(1, isAvailable);
                stmt.setString(2, carBrand);
                stmt.setString(3, carModel);

                int rowsUpdated = stmt.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update Driver availability
    public void updateDriverAvailability(String driverId, boolean isDriverAvailable) {
        String sql = "UPDATE Driver SET isDriverAvailable = ? WHERE driverId = ?";

        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setBoolean(1, isDriverAvailable);
                stmt.setString(2, driverId);
                int rowsUpdated = stmt.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
