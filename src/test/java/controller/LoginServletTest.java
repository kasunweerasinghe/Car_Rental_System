/**
 * created by kasun weerasinghe
 * Date: 2/28/25
 * Time: 11:33 AM
 * Project Name: CarRentalSystem
 */

package controller;

import com.carrental.carrentalsystem.controller.LoginServlet;
import com.carrental.carrentalsystem.model.User;
import com.carrental.carrentalsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginServlet loginServlet;

    private StringWriter stringWriter;
    private PrintWriter printWriter;


    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void testAdminLogin() throws Exception {
        // Arrange
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin123");
        when(request.getSession()).thenReturn(session);


        // Use reflection to invoke protected doPost method
        Method doPostMethod = LoginServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPostMethod.setAccessible(true); // Allow access to the protected method
        doPostMethod.invoke(loginServlet, request, response); // Invoke the method

        // Assert
        verify(session).setAttribute("username", "admin");
        verify(session).setAttribute("role", "admin");
        assertEquals("admin", stringWriter.toString().trim());
    }

    @Test
    void testCustomerLogin() throws Exception {
        // Arrange
        when(request.getParameter("username")).thenReturn("customer");
        when(request.getParameter("password")).thenReturn("password123");
        when(request.getSession()).thenReturn(session);

        User user = new User(1, "customer", "password123", "customer@example.com", "customer");
        when(userService.authenticateUser("customer", "password123")).thenReturn(user);

        // Use reflection to invoke protected doPost method
        Method doPostMethod = LoginServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPostMethod.setAccessible(true); // Allow access to the protected method
        doPostMethod.invoke(loginServlet, request, response); // Invoke the method

        // Assert
        verify(session).setAttribute("username", "customer");
        verify(session).setAttribute("role", "customer");
        assertEquals("customer", stringWriter.toString().trim());
    }

    @Test
    void testInvalidLogin() throws Exception {
        // Arrange
        when(request.getParameter("username")).thenReturn("invalid");
        when(request.getParameter("password")).thenReturn("invalid");
        when(userService.authenticateUser("invalid", "invalid")).thenReturn(null);

        // Use reflection to invoke protected doPost method
        Method doPostMethod = LoginServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPostMethod.setAccessible(true); // Allow access to the protected method
        doPostMethod.invoke(loginServlet, request, response); // Invoke the method

        // Assert
        assertEquals("error", stringWriter.toString().trim());
    }

}
