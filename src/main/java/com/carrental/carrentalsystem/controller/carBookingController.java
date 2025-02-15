/**
 * created by kasun weerasinghe
 * Date: 2/15/25
 * Time: 9:43 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.model.Booking;
import com.carrental.carrentalsystem.model.Car;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/booking")
public class carBookingController extends HttpServlet {

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
            String driverName = request.getParameter("driverName");
            String driverId = request.getParameter("driverId");
            int driverAge = Integer.parseInt(request.getParameter("driverAge"));

            // Convert java.util.Date → java.sql.Date
            java.sql.Date currentDate = java.sql.Date.valueOf(currentDateStr);
            java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
            java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

            Booking booking = new Booking(carId, customerName, currentDate, carBrand, carModel, price, pickupLocation,
                    dropLocation, startDate, endDate, driverName, driverId, driverAge);

            System.out.println(booking);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error: " + e.getMessage());
        }
    }
}
