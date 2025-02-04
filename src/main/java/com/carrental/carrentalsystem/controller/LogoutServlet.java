/**
 * created by kasun weerasinghe
 * Date: 1/29/25
 * Time: 7:22 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // End session
        }
        response.sendRedirect(request.getContextPath() + "/index.html"); // Redirect user
    }
}


