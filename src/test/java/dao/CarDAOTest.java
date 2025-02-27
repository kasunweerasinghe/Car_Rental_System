package dao;

import com.carrental.carrentalsystem.dao.CarDAO;
import com.carrental.carrentalsystem.model.Car;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class CarDAOTest {
    private static Connection connection;
    private static CarDAO carDAO;

    private static Car dummyCar;

    @BeforeAll
    public static void setup() throws SQLException {
        System.setProperty("test.env", "true"); // Set the system property for H2

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUser("root");
        dataSource.setPassword("Kasun2023..");

        connection = dataSource.getConnection();
        carDAO = new CarDAO(connection); // Pass the connection to CarDAO

        createTables(); // Ensure schema is created in H2
        dummyCar = saveDummyCar();
    }

    private static void createTables() throws SQLException {
        String createTableSQL = "CREATE TABLE Car (" +
                "carId VARCHAR(25) PRIMARY KEY, " +
                "brand VARCHAR(5), " +
                "model VARCHAR(25), " +
                "year INT, " +
                "price DOUBLE, " +
                "isAvailable BOOLEAN" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    public static Car saveDummyCar() {
        Car car = new Car("CAR-099", "Toyota", "Corolla", 2023, 100.0, true);
        carDAO.addCar(car);
        return car;
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Save car test")
    public void testAddCar() {
        Car car = new Car("CAR-069", "Toyota", "Corolla", 2023, 100.0, true);
        assertTrue(carDAO.addCar(car));
    }

    @Test
    @Order(2)
    @DisplayName("Update car test")
    public void testUpdateCar() {
        Double price = 200.0;
        // get car1
        Car initialCar = carDAO.getCar(dummyCar.getCarId());

        // assert > car1.getPrice = 1000
        assertTrue(initialCar.getPrice() == 100.0);

        // car1.setPrice = 2000
        initialCar.setPrice(200);
        // updateCar(car1)
        assertTrue(carDAO.updateCar(initialCar));

        //get car1
        Car updatedCar = carDAO.getCar(dummyCar.getCarId());
        //assert > car1.getPrice = 2000
        assertTrue(updatedCar.getPrice() == price);
    }

    @Test
    @Order(3)
    @DisplayName("Delete car test")
    public void testDeleteCar() {
        // Ensure the dummy car exists
        Car car = carDAO.getCar(dummyCar.getCarId());
        assertNotNull("dummyCar should exist", car);

        // Delete the car using its carId (a String)
        boolean isDeleted = carDAO.deleteCar(dummyCar.getCarId()); // Pass carId, not the Car object
        assertTrue("Car should be deleted successfully", isDeleted);

        // Verify the car is deleted
        Car deletedCar = carDAO.getCar(dummyCar.getCarId());
        assertNull("Car should be null after deletion", deletedCar);

        // Attempt to delete a non-existent car
        boolean isNonExistentCarDeleted = carDAO.deleteCar("NON_EXISTENT_CAR_ID");
        assertFalse("Deleting a non-existent car should return false", isNonExistentCarDeleted);
    }
}