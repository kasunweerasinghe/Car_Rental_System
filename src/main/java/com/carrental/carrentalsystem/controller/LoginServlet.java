/**
 * created by kasun weerasinghe
 * Date: 1/28/25
 * Time: 7:30 PM
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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ("admin".equals(username) && "admin123".equals(password)) {
            response.getWriter().write("admin"); // Send "admin" as response
        } else {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM Customer WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    response.getWriter().write("customer"); // Send "customer" as response
                } else {
                    response.getWriter().write("error"); // Invalid credentials
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("error");
            }
        }
    }
}
