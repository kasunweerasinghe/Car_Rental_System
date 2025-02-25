package com.carrental.carrentalsystem.dao;

import com.carrental.carrentalsystem.model.Car;

import java.util.List;

public interface CarDAOInterface {
    boolean addCar(Car car);

    List<Car> getAllCars();

    boolean deleteCar(String carId);

    boolean updateCar(Car car);

    int getCarCount();
}
