/**
 * created by kasun weerasinghe
 * Date: 2/27/25
 * Time: 6:42 PM
 * Project Name: CarRentalSystem
 */

package controller;


import com.carrental.carrentalsystem.controller.DriverServlet;
import com.carrental.carrentalsystem.model.Driver;
import com.carrental.carrentalsystem.service.CarService;
import com.carrental.carrentalsystem.service.DriverService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DriverServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private DriverService driverService;

    private DriverServlet driverServlet;
    private Gson gson;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        driverServlet = new DriverServlet();

        // Inject the mocked DriverDAO using reflection
        Field driverDAOField = DriverServlet.class.getDeclaredField("driverDAO");
        driverDAOField.setAccessible(true);
        driverDAOField.set(driverServlet, driverService);

        gson = new Gson();
    }

    @Test
    void testDoPost() throws Exception {
        // Mock request parameters
        when(request.getParameter("driverId")).thenReturn("DID-001");
        when(request.getParameter("driverName")).thenReturn("Test Driver");
        when(request.getParameter("driverAddress")).thenReturn("colombo");
        when(request.getParameter("driverAge")).thenReturn("20");
        when(request.getParameter("driverNationalId")).thenReturn("199876463734");

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Mock DrierDAO behavior
        when(driverService.addDriver(any(Driver.class))).thenReturn(true);

        // Use reflection to invoke protected doPost method
        Method doPostMethod = DriverServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPostMethod.setAccessible(true); // Allow access to the protected method
        doPostMethod.invoke(driverServlet, request, response); // Invoke the method

        // Verify the response
        printWriter.flush();
        assertEquals("success", stringWriter.toString().trim());
    }

    @Test
    void testDoGet() throws Exception {
        // Mock DriverDAO behavior
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("DID-001", "Test Driver", "colombo", 20, "199876463734", true));
        when(driverService.getAllDrivers()).thenReturn(drivers);
        when(driverService.getDriverCount()).thenReturn(1);

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Use reflection to invoke the protected doGet method
        Method doGetMethod = DriverServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        doGetMethod.setAccessible(true); // Allow access to the protected method
        doGetMethod.invoke(driverServlet, request, response); // Invoke the method

        // Verify the response
        printWriter.flush();
        String expectedJson = gson.toJson(drivers);
        assertEquals(expectedJson, stringWriter.toString());
    }

    @Test
    void testDoDelete() throws Exception {
        // Mock request parameter
        when(request.getParameter("id")).thenReturn("DID-001");

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Mock DriverDAO behavior
        when(driverService.deleteDriver("DID-001")).thenReturn(true);

        try {
            // Use reflection to invoke the protected doDelete method
            Method doDeleteMethod = DriverServlet.class.getDeclaredMethod("doDelete", HttpServletRequest.class, HttpServletResponse.class);
            doDeleteMethod.setAccessible(true); // Allow access to the protected method
            doDeleteMethod.invoke(driverServlet, request, response); // Invoke the method

            // Verify the response
            printWriter.flush();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("message", "Driver deleted successfully");
            assertEquals(gson.toJson(jsonResponse), stringWriter.toString());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new AssertionError("Method doDelete not found in DriverServlet. Check if it's declared correctly.", e);
        }
    }

    @Test
    void testDoPut() throws Exception {
        // Mock request body
        Driver driver = new Driver("DID-001", "Test Driver", "colombo", 20, "19944543463734", true);
        String jsonBody = gson.toJson(driver);
        BufferedReader reader = new BufferedReader(new StringReader(jsonBody));
        when(request.getReader()).thenReturn(reader);

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Mock DriverDAO behavior
        when(driverService.updateDriver(driver)).thenReturn(true);

        // Use reflection to invoke doPut
        Method doPutMethod = DriverServlet.class.getDeclaredMethod("doPut", HttpServletRequest.class, HttpServletResponse.class);
        doPutMethod.setAccessible(true);
        doPutMethod.invoke(driverServlet, request, response);

        // Verify the response
        printWriter.flush();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Driver updated successfully");
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
        Method doDeleteMethod = DriverServlet.class.getDeclaredMethod("doDelete", HttpServletRequest.class, HttpServletResponse.class);
        doDeleteMethod.setAccessible(true);
        doDeleteMethod.invoke(driverServlet, request, response);

        // Verify the response
        printWriter.flush();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Driver ID is required");
        assertEquals(gson.toJson(jsonResponse), stringWriter.toString());
    }

    @Test
    void testDoPutWithMissingId() throws Exception {
        // Mock request body (missing carId)
        Driver driver = new Driver(null, "test", "colombo", 20, "1993758474857", true);
        String jsonBody = gson.toJson(driver);
        BufferedReader reader = new BufferedReader(new StringReader(jsonBody));
        when(request.getReader()).thenReturn(reader);

        // Mock response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Use reflection to invoke doPut
        Method doPutMethod = DriverServlet.class.getDeclaredMethod("doPut", HttpServletRequest.class, HttpServletResponse.class);
        doPutMethod.setAccessible(true);
        doPutMethod.invoke(driverServlet, request, response);

        // Verify the response
        printWriter.flush();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Driver ID is required");
        assertEquals(gson.toJson(jsonResponse), stringWriter.toString());
    }

}
