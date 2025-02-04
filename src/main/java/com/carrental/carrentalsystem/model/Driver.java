/**
 * created by kasun weerasinghe
 * Date: 2/4/25
 * Time: 5:49 PM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.model;

public class Driver {
    private String driverId;
    private String name;
    private String address;
    private int age;
    private String nationalId;

    public Driver() {
    }

    public Driver(String driverId, String name, String address, int age, String nationalId) {
        this.driverId = driverId;
        this.name = name;
        this.address = address;
        this.age = age;
        this.nationalId = nationalId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
}
