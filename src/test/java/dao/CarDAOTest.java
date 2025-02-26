///**
// * created by kasun weerasinghe
// * Date: 2/26/25
// * Time: 3:03 PM
// * Project Name: CarRentalSystem
// */
//
//package dao;
//
//import com.carrental.carrentalsystem.dao.CarDAO;
//import com.carrental.carrentalsystem.model.Car;
//import org.h2.jdbcx.JdbcDataSource;
//import org.junit.jupiter.api.*;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.fail;
//
//public class CarDAOTest {
//    private static Connection connection;
//    private static CarDAO carDAO;
//
//    private static Car dummyCar;
//
//    @BeforeAll
//    public static void setup() throws SQLException {
//        JdbcDataSource dataSource = new JdbcDataSource();
//        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
//        dataSource.setUser("root");
//        dataSource.setPassword("kasun2023..");
//
//        connection = dataSource.getConnection();
//        carDAO = new CarDAO(connection); // Pass the connection to CarDAO
//
//        createTables(); // Ensure schema is created in H2
//        dummyCar = saveDummyCar();
//    }
//
//    private static void createTables() throws SQLException {
//        String createTableSQL = "CREATE TABLE Car (" +
//                "carId VARCHAR(255) PRIMARY KEY, " +
//                "brand VARCHAR(255), " +
//                "model VARCHAR(255), " +
//                "year INT, " +
//                "price DOUBLE, " +
//                "available BOOLEAN" +
//                ")";
//        try (Statement stmt = connection.createStatement()) {
//            stmt.execute(createTableSQL);
//        }
//    }
//
//
//    public static Car saveDummyCar() {
//        Car car = new Car("CAR12345", "Toyota", "Corolla", 2023, 100.0, true);
//        carDAO.addCar(car);
//        return car;
//    }
//
//    @AfterAll
//    public static void tearDown() throws SQLException {
//        if (connection != null) {
//            connection.close();
//        }
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("Save car test")
//    public void testAddCar() {
//        Car car = new Car("car1234", "Toyota", "Corolla", 2023, 100.0, true);
//        assertTrue(carDAO.addCar(car));
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("Update car test")
//    public void testUpdateCar() {
//        Double price = 200.0;
//        // get car1
//        Car initialCar = carDAO.getCar(dummyCar.getCarId());
//
//        // assert > car1.getPrice = 1000
//        assertTrue(initialCar.getPrice() == 100.0);
//
//        // car1.setPrice = 2000
//        initialCar.setPrice(200);
//        // updateCar(car1)
//        assertTrue(carDAO.updateCar(initialCar));
//
//        //get car1
//        Car updatedCar = carDAO.getCar(dummyCar.getCarId());
//        //assert > car1.getPrice = 2000
//        assertTrue(updatedCar.getPrice() == price);
//    }
//}
