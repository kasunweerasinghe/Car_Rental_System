/**
 * created by kasun weerasinghe
 * Date: 2/23/25
 * Time: 10:04 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.dao.BookingDAO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookingDetails")
public class bookingDetailsServlet extends HttpServlet {
    private BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(bookingDAO.getAllBookings()));

    }
}
