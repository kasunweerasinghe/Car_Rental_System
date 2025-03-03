/**
 * created by kasun weerasinghe
 * Date: 2/28/25
 * Time: 10:43 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.service;

import com.carrental.carrentalsystem.dao.CarDataToBookingDAO;

import java.util.List;

public class CarDataToBookingService {
    private CarDataToBookingDAO carDataToBookingDAO;

    public CarDataToBookingService() {
        this.carDataToBookingDAO = new CarDataToBookingDAO();
    }

    // Get all car brands
    public List<String> getCarBrands() {
        return carDataToBookingDAO.getCarBrands();
    }

    // Get car models by brand
    public List<String> getCarModelsByBrand(String brand) {
        if (brand == null || brand.isEmpty()) {
            throw new IllegalArgumentException("Brand is required");
        }

        return carDataToBookingDAO.getCarModelsByBrand(brand);
    }

    // Get car price by brand and model
    public int getCarPrice(String brand, String model) {
        if (brand == null || brand.isEmpty() || model == null || model.isEmpty()) {
            throw new IllegalArgumentException("Brand and model are required");
        }

        return carDataToBookingDAO.getCarPrice(brand, model);
    }

}
