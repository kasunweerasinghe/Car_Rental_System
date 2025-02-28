/**
 * created by kasun weerasinghe
 * Date: 2/27/25
 * Time: 5:52 PM
 * Project Name: CarRentalSystem
 */

package controller;

import com.carrental.carrentalsystem.controller.CarServlet;
import com.carrental.carrentalsystem.dao.CarDAO;
import com.carrental.carrentalsystem.model.Car;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CarServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CarDAO carDAO;

    private CarServlet carServlet;
    private Gson gson;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        carServlet = new CarServlet();

        // Inject the mocked CarDAO using reflection
        Field carDAOField = CarServlet.class.getDeclaredField("carDAO");
        carDAOField.setAccessible(true);
        carDAOField.set(carServlet, carDAO);

        gson = new Gson();
    }

    @Test
    void testDoPost() throws Exception {
        // Mock request parameters
        when(request.getParameter("carId")).thenReturn("CAR-001");
        when(request.getParameter("brand")).thenReturn("Toyota");
        when(request.getParameter("model")).thenReturn("Corolla");
        when(request.getParameter("year")).thenReturn("2023");
        when(request.getParameter("price")).thenReturn("100.0");

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Mock CarDAO behavior
        when(carDAO.addCar(any(Car.class))).thenReturn(true);

        // Use reflection to invoke protected doPost method
        Method doPostMethod = CarServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPostMethod.setAccessible(true); // Allow access to the protected method
        doPostMethod.invoke(carServlet, request, response); // Invoke the method

        // Verify the response
        printWriter.flush();
        assertEquals("success", stringWriter.toString().trim());
    }

    @Test
    void testDoGet() throws Exception {
        // Mock CarDAO behavior
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("CAR001", "Toyota", "Corolla", 2023, 100.0, true));
        when(carDAO.getAllCars()).thenReturn(cars);
        when(carDAO.getCarCount()).thenReturn(1);

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Use reflection to invoke the protected doGet method
        Method doGetMethod = CarServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        doGetMethod.setAccessible(true); // Allow access to the protected method
        doGetMethod.invoke(carServlet, request, response); // Invoke the method

        // Verify the response
        printWriter.flush();
        String expectedJson = gson.toJson(cars);
        assertEquals(expectedJson, stringWriter.toString());
    }


    @Test
    void testDoDelete() throws Exception {
        // Mock request parameter
        when(request.getParameter("id")).thenReturn("CAR001");

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Mock CarDAO behavior
        when(carDAO.deleteCar("CAR001")).thenReturn(true);

        try {
            // Use reflection to invoke the protected doDelete method
            Method doDeleteMethod = CarServlet.class.getDeclaredMethod("doDelete", HttpServletRequest.class, HttpServletResponse.class);
            doDeleteMethod.setAccessible(true); // Allow access to the protected method
            doDeleteMethod.invoke(carServlet, request, response); // Invoke the method

            // Verify the response
            printWriter.flush();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("message", "Car deleted successfully");
            assertEquals(gson.toJson(jsonResponse), stringWriter.toString());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new AssertionError("Method doDelete not found in CarServlet. Check if it's declared correctly.", e);
        }
    }


    @Test
    void testDoPut() throws Exception {
        // Mock request body
        Car car = new Car("CAR001", "Toyota", "Corolla", 2023, 100.0, true);
        String jsonBody = gson.toJson(car);
        BufferedReader reader = new BufferedReader(new StringReader(jsonBody));
        when(request.getReader()).thenReturn(reader);

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Mock CarDAO behavior
        when(carDAO.updateCar(car)).thenReturn(true);

        // Use reflection to invoke doPut
        Method doPutMethod = CarServlet.class.getDeclaredMethod("doPut", HttpServletRequest.class, HttpServletResponse.class);
        doPutMethod.setAccessible(true);
        doPutMethod.invoke(carServlet, request, response);

        // Verify the response
        printWriter.flush();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Car updated successfully");
    }


    @Test
    void testDoDeleteWithMissingId() throws Exception {
        // Mock request parameter (missing ID)
        when(request.getParameter("id")).thenReturn(null);

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Use reflection to invoke doDelete
        Method doDeleteMethod = CarServlet.class.getDeclaredMethod("doDelete", HttpServletRequest.class, HttpServletResponse.class);
        doDeleteMethod.setAccessible(true);
        doDeleteMethod.invoke(carServlet, request, response);

        // Verify the response
        printWriter.flush();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Car ID is required");
        assertEquals(gson.toJson(jsonResponse), stringWriter.toString());
    }


    @Test
    void testDoPutWithMissingId() throws Exception {
        // Mock request body (missing carId)
        Car car = new Car(null, "Toyota", "Corolla", 2023, 100.0, true);
        String jsonBody = gson.toJson(car);
        BufferedReader reader = new BufferedReader(new StringReader(jsonBody));
        when(request.getReader()).thenReturn(reader);

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Use reflection to invoke doPut
        Method doPutMethod = CarServlet.class.getDeclaredMethod("doPut", HttpServletRequest.class, HttpServletResponse.class);
        doPutMethod.setAccessible(true);
        doPutMethod.invoke(carServlet, request, response);

        // Verify the response
        printWriter.flush();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Car ID is required");
        assertEquals(gson.toJson(jsonResponse), stringWriter.toString());
    }



}
