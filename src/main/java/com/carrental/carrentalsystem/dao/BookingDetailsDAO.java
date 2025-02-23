/**
 * created by kasun weerasinghe
 * Date: 2/23/25
 * Time: 11:40 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.dao;

import com.carrental.carrentalsystem.model.Booking;
import com.carrental.carrentalsystem.model.BookingDetails;
import com.carrental.carrentalsystem.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingDetailsDAO {

    // save checkOut data in DB
    public boolean placeBooking(BookingDetails bookingDetails) {
        String query = "INSERT INTO BookingDetails (bookId, customerName, date, carBrand, carModel, price, Balance, tripStartDate, tripEndDate, driverName, driverId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, bookingDetails.getBookingId());
            preparedStatement.setString(2, bookingDetails.getCustomerName());
            preparedStatement.setString(3, bookingDetails.getCurrentDate());
            preparedStatement.setString(4, bookingDetails.getCarBrand());
            preparedStatement.setString(5, bookingDetails.getCarModel());
            preparedStatement.setInt(6, bookingDetails.getTotalPrice());
            preparedStatement.setInt(7, bookingDetails.getBalance());
            preparedStatement.setString(8, bookingDetails.getStartDate());
            preparedStatement.setString(9, bookingDetails.getEndDate());
            preparedStatement.setString(10, bookingDetails.getDriverName());
            preparedStatement.setString(11, bookingDetails.getDriverId());

            // Log the SQL query and parameters
            System.out.println("Executing SQL: " + preparedStatement.toString());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the SQL exception
        }
        return false;
    }
}
