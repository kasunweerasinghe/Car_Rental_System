/**
 * created by kasun weerasinghe
 * Date: 2/23/25
 * Time: 11:23 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.model;

import java.sql.Date;

public class BookingDetails {
    private String bookingId;
    private String customerName;
    private String currentDate;
    private String carBrand;
    private String carModel;
    private int totalPrice;
    private int Balance;
    private String startDate;
    private String endDate;
    private String driverName;
    private String driverId;

    public BookingDetails() {
    }

    public BookingDetails(String bookingId, String customerName, String currentDate, String carBrand, String carModel, int totalPrice, int balance, String startDate, String endDate, String driverName, String driverId) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.currentDate = currentDate;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.totalPrice = totalPrice;
        Balance = balance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.driverName = driverName;
        this.driverId = driverId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
