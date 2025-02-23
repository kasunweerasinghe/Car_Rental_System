/**
 * created by kasun weerasinghe
 * Date: 2/15/25
 * Time: 9:43 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.dao.BookingDAO;
import com.carrental.carrentalsystem.dao.CarDAO;
import com.carrental.carrentalsystem.dao.DriverDAO;
import com.carrental.carrentalsystem.model.Booking;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/booking")
public class carBookingServlet extends HttpServlet {
    private BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String carId = request.getParameter("bookingId");
            String customerName = request.getParameter("customerName");
            String currentDateStr = request.getParameter("currentDate");
            String carBrand = request.getParameter("carBrand");
            String carModel = request.getParameter("carModel");
            int price = Integer.parseInt(request.getParameter("price"));
            String pickupLocation = request.getParameter("pickupLocation");
            String dropLocation = request.getParameter("dropLocation");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
            String driverName = request.getParameter("driverName");
            String driverId = request.getParameter("driverId");
            int driverAge = Integer.parseInt(request.getParameter("driverAge"));

            // Convert java.util.Date → java.sql.Date
            java.sql.Date currentDate = java.sql.Date.valueOf(currentDateStr);
            java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
            java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

            Booking booking = new Booking(carId, customerName, currentDate, carBrand, carModel, price, pickupLocation,
                    dropLocation, startDate, endDate, totalPrice, driverName, driverId, driverAge);

            boolean isAdded = bookingDAO.placeBooking(booking);
            if (isAdded) {
                response.getWriter().write("success");
                bookingDAO.updateCarAvailability(booking.getCarBrand(), booking.getCarModel(), false);
                bookingDAO.updateDriverAvailability(booking.getDriverId(), false);
            } else {
                response.getWriter().write("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(bookingDAO.getAllBookings()));

    }
}
