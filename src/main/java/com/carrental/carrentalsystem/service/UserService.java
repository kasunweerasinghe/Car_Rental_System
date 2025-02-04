/**
 * created by kasun weerasinghe
 * Date: 2/4/25
 * Time: 9:07 AM
 * Project Name: CarRentalSystem
 */

package com.carrental.carrentalsystem.service;

import com.carrental.carrentalsystem.dao.UserDAO;
import com.carrental.carrentalsystem.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User authenticateUser(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    public boolean registerUser(User user) {
        return userDAO.insertUser(user);
    }
}
