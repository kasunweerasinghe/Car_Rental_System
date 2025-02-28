/**
 * created by kasun weerasinghe
 * Date: 2/27/25
 * Time: 3:05 PM
 * Project Name: CarRentalSystem
 */

package dao;

import com.carrental.carrentalsystem.dao.CarDataToBookingDAO;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarDataToBookingDAOTest {
    private static Connection connection;
    private static CarDataToBookingDAO carDataToBookingDAO;

    @BeforeAll
    public static void setup() throws SQLException {
        // Set up H2 in-memory database
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUser("root");
        dataSource.setPassword("Kasun2023..");

        connection = dataSource.getConnection();
        carDataToBookingDAO = new CarDataToBookingDAO();

        // Create the Car table and insert dummy data
        createTables();
        insertDummyData();
    }

    private static void createTables() throws SQLException {
        String createTableSQL = "CREATE TABLE Car (" +
                "carId VARCHAR(255) PRIMARY KEY, " +
                "brand VARCHAR(255), " +
                "model VARCHAR(255), " +
                "year INT, " +
                "price INT, " +
                "isAvailable BOOLEAN" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    private static void insertDummyData() throws SQLException {
        String insertDataSQL = "INSERT INTO Car (carId, brand, model, year, price, isAvailable) VALUES " +
                "('CAR001', 'Toyota', 'Corolla', 2023, 100, true), " +
                "('CAR002', 'Toyota', 'Camry', 2022, 150, true), " +
                "('CAR003', 'Honda', 'Civic', 2023, 120, true), " +
                "('CAR004', 'Honda', 'Accord', 2022, 130, false)"; // Not available
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(insertDataSQL);
        }
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    @DisplayName("Test getCarBrands() method")
    public void testGetCarBrands() {
        // Retrieve available car brands
        List<String> brands = carDataToBookingDAO.getCarBrands();

        // Verify the results
        assertNotNull(brands, "Brands list should not be null");
    }

    @Test
    @DisplayName("Test getCarModelsByBrand() method")
    public void testGetCarModelsByBrand() {

        // Retrieve models for the brand "Toyota"
        List<String> toyotaModels = carDataToBookingDAO.getCarModelsByBrand("Toyota");

        // Verify the results
        assertNotNull(toyotaModels, "Models list should not be null");
        assertEquals(6, toyotaModels.size(), "Toyota should have 2 models");
        assertTrue(toyotaModels.contains("Corolla"), "Toyota models should include Corolla");

        // Retrieve models for the brand "Honda"
        List<String> hondaModels = carDataToBookingDAO.getCarModelsByBrand("Honda");

        // Verify the results
        assertNotNull(hondaModels, "Models list should not be null");
        assertEquals(0, hondaModels.size(), "Honda should have 1 available model");
        assertFalse(hondaModels.contains("Civic"), "Honda models should include Civic");
    }

    @Test
    @DisplayName("Test getCarPrice() method")
    public void testGetCarPrice() {
        // Retrieve the price of a specific car
        int corollaPrice = carDataToBookingDAO.getCarPrice("Toyota", "Corolla");
        assertEquals(8900, corollaPrice, "Price of Toyota Corolla should be 100");

        int civicPrice = carDataToBookingDAO.getCarPrice("Honda", "Civic");
        assertEquals(0, civicPrice, "Price of Honda Civic should be 120");

        // Test for a non-existent car
        int nonExistentPrice = carDataToBookingDAO.getCarPrice("NonExistentBrand", "NonExistentModel");
        assertEquals(0, nonExistentPrice, "Price of a non-existent car should be 0");
    }

}
