/**
 * created by kasun weerasinghe
 * Date: 2/8/25
 * Time: 12:14 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.dao;

import com.carrental.carrentalsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDataToBookingDAO {

    // Method to get all car brands
    public List<String> getCarBrands() {
        List<String> brands = new ArrayList<>();
        String query = "SELECT DISTINCT brand FROM Car";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                brands.add(rs.getString("brand"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brands;
    }

    // Method to get models based on the selected brand
    public List<String> getCarModelsByBrand(String brand) {
        List<String> models = new ArrayList<>();
        String query = "SELECT model FROM Car WHERE brand = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, brand);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    models.add(rs.getString("model"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return models;
    }
}
