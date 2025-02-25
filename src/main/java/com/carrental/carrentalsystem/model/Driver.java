/**
 * created by kasun weerasinghe
 * Date: 2/4/25
 * Time: 5:49 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.model;

public class Driver {
    private String driverId;
    private String driverName;
    private String driverAddress;
    private int driverAge;
    private String driverNationalId;
    private boolean isDriverAvailable;

    public Driver() {
    }

    public Driver(String driverId, String driverName, String driverAddress, int driverAge, String driverNationalId, boolean isDriverAvailable) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverAddress = driverAddress;
        this.driverAge = driverAge;
        this.driverNationalId = driverNationalId;
        this.isDriverAvailable = isDriverAvailable;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }

    public int getDriverAge() {
        return driverAge;
    }

    public void setDriverAge(int driverAge) {
        this.driverAge = driverAge;
    }

    public String getDriverNationalId() {
        return driverNationalId;
    }

    public void setDriverNationalId(String driverNationalId) {
        this.driverNationalId = driverNationalId;
    }

    public boolean isDriverAvailable() {
        return isDriverAvailable;
    }

    public void setDriverAvailable(boolean driverAvailable) {
        isDriverAvailable = driverAvailable;
    }
}
