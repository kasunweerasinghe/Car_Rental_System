/**
 * created by kasun weerasinghe
 * Date: 1/28/25
 * Time: 7:27 PM
 * Project Name: CarRentalSystem
 */


import com.carrental.carrentalsystem.util.DatabaseConnection;

import java.sql.Connection;

public class DatabaseTest {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
