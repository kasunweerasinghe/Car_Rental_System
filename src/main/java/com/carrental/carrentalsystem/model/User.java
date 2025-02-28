/**
 * created by kasun weerasinghe
 * Date: 2/4/25
 * Time: 9:03 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    public User() {
    }

    public User(int id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String number, String test, String test123, String mail) {

    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
