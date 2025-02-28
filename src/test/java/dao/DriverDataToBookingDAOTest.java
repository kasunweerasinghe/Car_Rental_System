/**
 * created by kasun weerasinghe
 * Date: 2/27/25
 * Time: 3:29 PM
 * Project Name: CarRentalSystem
 */

package dao;

import com.carrental.carrentalsystem.dao.DriverDataToBookingDAO;
import com.carrental.carrentalsystem.model.Driver;
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

public class DriverDataToBookingDAOTest {
    private static Connection connection;
    private static DriverDataToBookingDAO driverDataToBookingDAO;

    @BeforeAll
    public static void setup() throws SQLException {
        // Set up H2 in-memory database
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUser("root");
        dataSource.setPassword("Kasun2023..");

        connection = dataSource.getConnection();
        driverDataToBookingDAO = new DriverDataToBookingDAO();

        // Create the Driver table and insert dummy data
        createTables();
        insertDummyData();
    }

    private static void createTables() throws SQLException {
        String createTableSQL = "CREATE TABLE Driver (" +
                "driverId VARCHAR(255) PRIMARY KEY, " +
                "driverName VARCHAR(255), " +
                "driverAge INT, " +
                "driverNationalId VARCHAR(255), " +
                "isDriverAvailable BOOLEAN" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    private static void insertDummyData() throws SQLException {
        String insertDataSQL = "INSERT INTO Driver (driverId, driverName, driverAge, driverNationalId, isDriverAvailable) VALUES " +
                "('DRV001', 'John Doe', 30, '199875638262',true), " +
                "('DRV002', 'Jane Smith', 25, '200175638262', true), " +
                "('DRV003', 'Mike Johnson', 40, '197267438262', false)"; // Not available
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
    @DisplayName("Test getAvailableDrivers() method")
    public void testGetAvailableDrivers() {
        // Retrieve available drivers
        List<Driver> drivers = driverDataToBookingDAO.getAvailableDrivers();

        // Verify the results
        assertNotNull(drivers, "Drivers list should not be null");
        assertEquals(1, drivers.size(), "There should be 2 available drivers");

        // Verify the details of the first driver
        Driver firstDriver = drivers.get(0);
        assertEquals("DID-002", firstDriver.getDriverId(), "Driver ID should match");
        assertEquals("Pasan", firstDriver.getDriverName(), "Driver name should match");
        assertEquals(20, firstDriver.getDriverAge(), "Driver age should match");
        assertTrue(firstDriver.isDriverAvailable(), "Driver should be available");
    }

}
