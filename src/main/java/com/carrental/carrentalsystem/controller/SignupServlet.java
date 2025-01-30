/**
 * created by kasun weerasinghe
 * Date: 1/28/25
 * Time: 8:14 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.dao.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String role = "CUSTOMER";

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Customer (username, password, email, role) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, role);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                response.sendRedirect("index.html?success=true"); // Redirect to HTML version
            } else {
                response.sendRedirect("signup.html?error=Registration failed. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("signup.html?error=Database error. Please try again later.");
        }
    }
}
