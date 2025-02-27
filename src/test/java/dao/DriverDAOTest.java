/**
 * created by kasun weerasinghe
 * Date: 2/27/25
 * Time: 2:14 PM
 * Project Name: CarRentalSystem
 */

package dao;

import com.carrental.carrentalsystem.dao.DriverDAO;
import com.carrental.carrentalsystem.model.Driver;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class DriverDAOTest {
    private static Connection connection;
    private static DriverDAO driverDAO;

    private static Driver dummyDriver;

    @BeforeAll
    public static void setup() throws SQLException {
        // Set the system property for H2
        System.setProperty("test.env", "true");

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUser("root");
        dataSource.setPassword("Kasun2023..");

        connection = dataSource.getConnection();
        driverDAO = new DriverDAO(connection); // Pass the connection to CarDAO

        createTables(); // Ensure schema is created in H2
        dummyDriver = saveDummyDriver();
    }

    private static void createTables() throws SQLException {
        String createTableSQL = "CREATE TABLE Driver (" +
                "driverId VARCHAR(25) PRIMARY KEY, " +
                "driverName VARCHAR(25), " +
                "driverAddress VARCHAR(25), " +
                "driverAge INT, " +
                "driverNationalId VARCHAR(25), " +
                "isDriverAvailable BOOLEAN" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    public static Driver saveDummyDriver() {
        Driver driver = new Driver("DID-078", "Mihithilina", "Godagama", 10, "200067656734", true);
        DriverDAO.addDriver(driver);
        return driver;
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Save Driver test")
    public void testAddDriver() {
        Driver driver = new Driver("DID-068", "Gayan", "Colombo", 19, "19992265425", true);
        assertTrue(DriverDAO.addDriver(driver));
    }

    @Test
    @Order(2)
    @DisplayName("Update Driver test")
    public void testUpdateDriver() {
        Integer age = 20;

        Driver initialDriver = driverDAO.getDriver(dummyDriver.getDriverId());
        assertTrue(initialDriver.getDriverAge() == 10);

        initialDriver.setDriverAge(age);
        assertTrue(driverDAO.updateDriver(initialDriver));

        Driver updatedDriver = driverDAO.getDriver(dummyDriver.getDriverId());

        assertTrue(updatedDriver.getDriverAge() == age);
    }

    @Test
    @Order(3)
    @DisplayName("Delete Driver test")
    public void testDeleteDriver() {

        Driver driver = driverDAO.getDriver(dummyDriver.getDriverId());
        assertNotNull("dummyDriver should exist", driver);

        boolean isDeleted = driverDAO.deleteDriver(dummyDriver.getDriverId()); // Pass driverId, not the Driver object
        assertTrue("Driver should be deleted successfully", isDeleted);

        Driver deletedDriver = driverDAO.getDriver(dummyDriver.getDriverId());
        assertNull("Driver should be null after deletion", deletedDriver);

        boolean isNonExistentDriverDeleted = driverDAO.deleteDriver("NON_EXISTENT_DRIVER_ID");
        assertFalse("Deleting a non-existent driver should return false", isNonExistentDriverDeleted);
    }

}
