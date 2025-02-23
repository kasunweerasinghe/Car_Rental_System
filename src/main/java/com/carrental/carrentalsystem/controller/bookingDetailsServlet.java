/**
 * created by kasun weerasinghe
 * Date: 2/23/25
 * Time: 10:04 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.dao.BookingDAO;
import com.carrental.carrentalsystem.dao.BookingDetailsDAO;
import com.carrental.carrentalsystem.model.BookingDetails;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/bookingDetails")
public class bookingDetailsServlet extends HttpServlet {
    private BookingDAO bookingDAO = new BookingDAO();
    private BookingDetailsDAO bookingDetailsDAO = new BookingDetailsDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read JSON data from request
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        // Convert JSON string to Java object
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBuffer.toString(), JsonObject.class);

        // Log the received JSON data
        System.out.println("Received JSON: " + jsonObject.toString());

        String bookingId = jsonObject.get("bookingId").getAsString();
        String customerName = jsonObject.get("customerName").getAsString();
        String currentDateStr = jsonObject.get("currentDate").getAsString();
        String carBrand = jsonObject.get("carBrand").getAsString();
        String carModel = jsonObject.get("carModel").getAsString();
        int totalPrice = jsonObject.get("totalPrice").getAsInt();
        int balance = jsonObject.get("balance").getAsInt();
        String startDateStr = jsonObject.get("startDate").getAsString();
        String endDateStr = jsonObject.get("endDate").getAsString();
        String driverName = jsonObject.get("driverName").getAsString();
        String driverId = jsonObject.get("driverId").getAsString();

        // Parse dates to ensure they are in the correct format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate, startDate, endDate;
        try {
            currentDate = dateFormat.parse(currentDateStr);
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            response.getWriter().write("error: Invalid date format");
            return;
        }

        BookingDetails bookingDetails = new BookingDetails(bookingId, customerName, dateFormat.format(currentDate), carBrand, carModel, totalPrice, balance, dateFormat.format(startDate), dateFormat.format(endDate), driverName, driverId);
        boolean isAdded = bookingDetailsDAO.placeBooking(bookingDetails);

        if (isAdded) {
            response.getWriter().write("success");
            bookingDAO.updateCarAvailability(bookingDetails.getCarBrand(), bookingDetails.getCarModel(), true);
            bookingDAO.updateDriverAvailability(bookingDetails.getDriverId(), true);
        } else {
            response.getWriter().write("error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(bookingDAO.getAllBookings()));

    }
}
