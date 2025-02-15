/**
 * created by kasun weerasinghe
 * Date: 2/8/25
 * Time: 8:20 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.dao;

import com.carrental.carrentalsystem.model.Driver;
import com.carrental.carrentalsystem.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DriverDataToBookingDAO {

    public List<Driver> getAvailableDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT driverId, driverName, driverAge, isDriverAvailable FROM Driver WHERE isDriverAvailable = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Driver driver = new Driver();
                driver.setDriverId(rs.getString("driverId"));
                driver.setDriverName(rs.getString("driverName"));
                driver.setDriverAge(rs.getInt("driverAge"));
                driver.setDriverAvailable(rs.getBoolean("isDriverAvailable"));
                drivers.add(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return drivers;
    }
}
