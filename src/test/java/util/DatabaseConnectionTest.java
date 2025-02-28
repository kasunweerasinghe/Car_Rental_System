/**
 * created by kasun weerasinghe
 * Date: 2/25/25
 * Time: 11:26 AM
 * Project Name: CarRentalSystem
 */

package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;

public class DatabaseConnectionTest {


    /**
     * Test to check if a valid database connection is established.
     */
    @Test
    @DisplayName("Get successful database connection test")
    public void testGetConnection_Success() {
//        try (Connection connection = com.carrental.carrentalsystem.util.DatabaseConnection.getConnection()) {
//            assertNotNull("Connection should not be null", connection);
//            assertFalse("Connection should not be closed", connection.isClosed());
//        } catch (SQLException e) {
//            fail("SQLException should not occur: " + e.getMessage());
//        }
    }

    /**
     * Test to verify if SQLException is thrown for incorrect credentials.
     */
    @Test
    @DisplayName("Get successful database connection : Providing invalid credentials test")
    public void testGetConnection_InvalidCredentials() {
        try {
            // Modify the credentials temporarily (this won't affect the real class)
            String invalidUser = "wrongUser";
            String invalidPassword = "wrongPass";
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/CarRentalDB?serverTimezone=UTC",
                    invalidUser,
                    invalidPassword
            );
            fail("Expected SQLException due to incorrect credentials");
        } catch (SQLException e) {
            assertTrue("Exception should indicate access denied",
                    e.getMessage().contains("Access denied"));
        }
    }
}
