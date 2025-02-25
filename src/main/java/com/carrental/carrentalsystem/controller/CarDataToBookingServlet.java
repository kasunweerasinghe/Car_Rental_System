package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.dao.CarDataToBookingDAO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/carBookingData")
public class CarDataToBookingServlet extends HttpServlet {
    CarDataToBookingDAO carDataToBookingDAO = new CarDataToBookingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("models".equals(action)) {
            String brand = request.getParameter("brand");
            List<String> models = carDataToBookingDAO.getCarModelsByBrand(brand);
            String json = new Gson().toJson(models);
            response.getWriter().write(json);
        } else if ("price".equals(action)) {
            String brand = request.getParameter("brand");
            String model = request.getParameter("model");
            int price = carDataToBookingDAO.getCarPrice(brand, model);

            // Creating a HashMap to return price in JSON format
            Map<String, Integer> priceMap = new HashMap<>();
            priceMap.put("price", price);

            String json = new Gson().toJson(priceMap);
            response.getWriter().write(json);
        } else {
            // Default: Load brands
            List<String> brands = carDataToBookingDAO.getCarBrands();
            String json = new Gson().toJson(brands);
            response.getWriter().write(json);
        }
    }
}
