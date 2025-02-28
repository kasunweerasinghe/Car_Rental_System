/**
 * created by kasun weerasinghe
 * Date: 2/4/25
 * Time: 10:20 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.dao.CarDAO;
import com.carrental.carrentalsystem.model.Car;
import com.carrental.carrentalsystem.service.CarService;
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


@WebServlet("/car")
public class CarServlet extends HttpServlet {
    private CarService carService = new CarService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String carId = request.getParameter("carId");
            String brand = request.getParameter("brand");
            String model = request.getParameter("model");
            int year = Integer.parseInt(request.getParameter("year"));
            double price = Double.parseDouble(request.getParameter("price"));

            Car car = new Car(carId, brand, model, year, price, true);
            boolean isAdded = carService.addCar(car);

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
        response.getWriter().write(new Gson().toJson(carService.getAllCars()));

        int carCount = carService.getCarCount();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("carCount", carCount);

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            jsonResponse.addProperty("message", "Car ID is required");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(jsonResponse);
            out.flush();
            return;
        }

        boolean isDeleted = carService.deleteCar(idParam);

        if (isDeleted) {
            jsonResponse.addProperty("message", "Car deleted successfully");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            jsonResponse.addProperty("message", "Car not found or could not be deleted");
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
        Car car = gson.fromJson(sb.toString(), Car.class);

        if (car.getCarId() == null || car.getCarId().isEmpty()) {
            jsonResponse.addProperty("message", "Car ID is required");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(jsonResponse);
            out.flush();
            return;
        }

        boolean isUpdated = carService.updateCar(car);

        if (isUpdated) {
            jsonResponse.addProperty("message", "Car updated successfully");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            jsonResponse.addProperty("message", "Car not found or could not be updated");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        out.print(jsonResponse);
        out.flush();
    }

}
