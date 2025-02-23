/**
 * created by kasun weerasinghe
 * Date: 2/15/25
 * Time: 9:59 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.model;

import java.sql.Date;

public class Booking {
    private String bookingId;
    private String customerName;
    private Date currentDate;
    private String carBrand;
    private String carModel;
    private int price;
    private String pickupLocation;
    private String dropLocation;
    private Date startDate;
    private Date endDate;
    private int totalPrice;
    private String driverName;
    private String driverId;
    private int driverAge;

    public Booking() {
    }

    public Booking(String bookingId, String customerName, Date currentDate, String carBrand, String carModel, int price, String pickupLocation, String dropLocation, Date startDate, Date endDate, int totalPrice, String driverName, String driverId, int driverAge) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.currentDate = currentDate;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.price = price;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.driverName = driverName;
        this.driverId = driverId;
        this.driverAge = driverAge;
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

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
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

    public int getDriverAge() {
        return driverAge;
    }

    public void setDriverAge(int driverAge) {
        this.driverAge = driverAge;
    }
}
