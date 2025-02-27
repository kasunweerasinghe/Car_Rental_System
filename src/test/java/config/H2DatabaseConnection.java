///**
// * created by kasun weerasinghe
// * Date: 2/26/25
// * Time: 3:33 PM
// * Project Name: CarRentalSystem
// */
//
//package config;
//
//import org.h2.tools.RunScript;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//
//import java.io.FileReader;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class H2DatabaseConnection {
//    private static Connection connection;
//
//    @BeforeAll
//    public static void setupDatabase() {
//        try {
//            // Initialize H2 in-memory database
//            connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
//            // Run an SQL script to create tables
//            RunScript.execute(connection, new FileReader("src/test/resources/schema.sql"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @AfterAll
//    public static void teardownDatabase() throws SQLException {
//        if (connection != null) {
//            connection.close();
//        }
//    }
//
//}
