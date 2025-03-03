/**
 * created by kasun weerasinghe
 * Date: 2/4/25
 * Time: 10:16 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.model;

public class Car {
    private String carId;
    private String brand;
    private String model;
    private int year;
    private double price;
    private boolean isAvailable;

    public Car() {
    }

    public Car(String carId, String brand, String model, int year, double price, boolean isAvailable) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
