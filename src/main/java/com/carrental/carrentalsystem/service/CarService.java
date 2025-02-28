/**
 * created by kasun weerasinghe
 * Date: 2/28/25
 * Time: 10:29 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.service;

import com.carrental.carrentalsystem.dao.CarDAO;
import com.carrental.carrentalsystem.model.Car;

import java.util.List;

public class CarService {
    private CarDAO carDAO;

    public CarService() {
        this.carDAO = new CarDAO();
    }

    public boolean addCar(Car car) {
        if (car.getCarId() == null || car.getBrand() == null || car.getModel() == null) {
            throw new IllegalArgumentException("Car ID, brand, and model are required");
        }

        Car existingCar = carDAO.getCar(car.getCarId());
        if (existingCar != null) {
            throw new RuntimeException("Car with ID " + car.getCarId() + " already exists");
        }

        return carDAO.addCar(car);
    }

    public boolean updateCar(Car car) {
        if (car.getCarId() == null) {
            throw new IllegalArgumentException("Car ID is required");
        }

        return carDAO.updateCar(car);
    }

    public boolean deleteCar(String carId) {
        if (carId == null || carId.isEmpty()) {
            throw new IllegalArgumentException("Car ID is required");
        }

        return carDAO.deleteCar(carId);
    }

    public List<Car> getAllCars() {
        // Fetch all cars from the database
        return carDAO.getAllCars();
    }

    public int getCarCount() {
        return carDAO.getCarCount();
    }

}
