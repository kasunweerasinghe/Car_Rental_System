/**
 * created by kasun weerasinghe
 * Date: 2/4/25
 * Time: 10:20 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.dao.CarDAO;
import com.carrental.carrentalsystem.model.Car;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/car")
public class CarServlet extends HttpServlet {
    private CarDAO carDAO = new CarDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        double price = Double.parseDouble(request.getParameter("price"));

        Car car = new Car(0, brand, model, year, price, true);
        boolean isAdded = carDAO.addCar(car);

        if (isAdded) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(carDAO.getAllCars()));

    }
}
