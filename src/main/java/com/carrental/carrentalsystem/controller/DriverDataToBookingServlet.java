/**
 * created by kasun weerasinghe
 * Date: 2/8/25
 * Time: 8:21 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.controller;

import com.carrental.carrentalsystem.dao.DriverDataToBookingDAO;
import com.carrental.carrentalsystem.model.Driver;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getAvailableDrivers")
public class DriverDataToBookingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DriverDataToBookingDAO dao = new DriverDataToBookingDAO();
        List<Driver> drivers = dao.getAvailableDrivers();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(drivers));
    }

}
