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
        String query = "INSERT INTO Booking (bookId, customerName, date, carBrand, carModel, price, pickupLocation, dropLocation, tripStartDate, tripEndDate, driverName, driverId, driverAge) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
            preparedStatement.setString(11, booking.getDriverName());
            preparedStatement.setString(12, booking.getDriverId());
            preparedStatement.setInt(13, booking.getDriverAge());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all cars from the database
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Booking";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
                booking.setDriverName(resultSet.getString("driverName"));
                booking.setDriverId(resultSet.getString("driverId"));
                booking.setDriverAge(resultSet.getInt("driverAge"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean updateCarAvailability(String carBrand, String carModel, boolean isAvailable) {
        String sql = "UPDATE Car SET isAvailable = ? WHERE brand = ? AND model = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isAvailable);
            stmt.setString(2, carBrand);
            stmt.setString(3, carModel);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
