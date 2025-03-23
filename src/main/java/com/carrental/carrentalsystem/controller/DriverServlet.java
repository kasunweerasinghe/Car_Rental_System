/**
 * created by kasun weerasinghe
 * Date: 2/4/25
 * Time: 5:52 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.dao.DriverDAO;
import com.carrental.carrentalsystem.model.Driver;
import com.carrental.carrentalsystem.service.DriverService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/driver")
public class DriverServlet extends HttpServlet {
    private DriverService driverService = new DriverService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String driverId = request.getParameter("driverId");
            String name = request.getParameter("driverName");
            String address = request.getParameter("driverAddress");
            int age = Integer.parseInt(request.getParameter("driverAge"));
            String nationalId = request.getParameter("driverNationalId");

            Driver driver = new Driver(driverId, name, address, age, nationalId, true);
            boolean isAdded = driverService.addDriver(driver);


            if (isAdded) {
                response.getWriter().write("success");
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
        int driverCount = driverService.getDriverCount();
        JsonObject jsonResponse = new JsonObject();
        response.getWriter().write(new Gson().toJson(driverService.getAllDrivers()));
        jsonResponse.addProperty("driverCount", driverCount);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

//        // Get all drivers
//        List<Driver> drivers = driverService.getAllDrivers();
//        response.getWriter().write(new Gson().toJson(drivers));
//
//        // Get driver count
//        int driverCount = driverService.getDriverCount();
//        JsonObject jsonResponse = new JsonObject();
//        jsonResponse.addProperty("driverCount", driverCount);
//        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            jsonResponse.addProperty("message", "Driver ID is required");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(jsonResponse);
            out.flush();
            return;
        }

        boolean isDeleted = driverService.deleteDriver(idParam);

        if (isDeleted) {
            jsonResponse.addProperty("message", "Driver deleted successfully");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            jsonResponse.addProperty("message", "Driver not found or could not be deleted");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        out.print(jsonResponse);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        // Read the JSON body
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        // Parse JSON body
        Gson gson = new Gson();
        Driver driver = gson.fromJson(sb.toString(), Driver.class);

        if (driver.getDriverId() == null || driver.getDriverId().isEmpty()) {
            jsonResponse.addProperty("message", "Driver ID is required");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(jsonResponse);
            out.flush();
            return;
        }

        boolean isUpdated = driverService.updateDriver(driver);

        if (isUpdated) {
            jsonResponse.addProperty("message", "Driver updated successfully");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            jsonResponse.addProperty("message", "Driver not found or could not be updated");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        out.print(jsonResponse);
        out.flush();
    }
}
