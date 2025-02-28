/**
 * created by kasun weerasinghe
 * Date: 2/28/25
 * Time: 10:37 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.service;

import com.carrental.carrentalsystem.dao.DriverDAO;
import com.carrental.carrentalsystem.model.Driver;

import java.util.List;

public class DriverService {
    private DriverDAO driverDAO;

    public DriverService() {
        this.driverDAO = new DriverDAO();
    }

    // Add a new driver
    public boolean addDriver(Driver driver) {
        if (driver.getDriverId() == null || driver.getDriverName() == null || driver.getDriverNationalId() == null) {
            throw new IllegalArgumentException("Driver ID, name, and national ID are required");
        }

        Driver existingDriver = driverDAO.getDriver(driver.getDriverId());
        if (existingDriver != null) {
            throw new RuntimeException("Driver with ID " + driver.getDriverId() + " already exists");
        }

        return driverDAO.addDriver(driver);
    }

    // Get all drivers
    public List<Driver> getAllDrivers() {
        return driverDAO.getAllDrivers();
    }

    // Get the total count of drivers
    public int getDriverCount() {
        return driverDAO.getDriverCount();
    }

    // Delete a driver
    public boolean deleteDriver(String driverId) {
        if (driverId == null || driverId.isEmpty()) {
            throw new IllegalArgumentException("Driver ID is required");
        }

        return driverDAO.deleteDriver(driverId);
    }

    // Update a driver
    public boolean updateDriver(Driver driver) {
        if (driver.getDriverId() == null) {
            throw new IllegalArgumentException("Driver ID is required");
        }

        return driverDAO.updateDriver(driver);
    }

}
