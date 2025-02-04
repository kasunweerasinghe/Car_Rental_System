/**
 * created by kasun weerasinghe
 * Date: 1/28/25
 * Time: 7:30 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.model.User;
import com.carrental.carrentalsystem.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Admin Hardcoded Check
        if ("admin".equals(username) && "admin123".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", "admin");
            session.setAttribute("role", "admin");
            response.getWriter().write("admin"); // Redirect to admin dashboard
            return;
        }

        User user = userService.authenticateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            response.getWriter().write("customer"); // Redirect to customer dashboard
        } else {
            response.getWriter().write("error"); // Invalid credentials
        }
    }
}
