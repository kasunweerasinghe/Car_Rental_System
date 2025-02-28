/**
 * created by kasun weerasinghe
 * Date: 1/28/25
 * Time: 8:14 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.model.User;
import com.carrental.carrentalsystem.service.UserService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        User newUser = new User(0, username, password, email, "customer");

        boolean isRegistered = userService.registerUser(newUser);

        if (isRegistered) {
            response.sendRedirect("index.html");
        } else {
            response.getWriter().write("error");
        }
    }
}
