package com.codecool.ccms.session;

import com.codecool.ccms.dao.UserDao;
import com.codecool.ccms.models.Student;
import com.codecool.ccms.models.User;
import com.codecool.ccms.ui.UI;

import java.util.List;

public class Login {
    public User loginAttempt(String userEmail, char[] userPassword) {
        List<User> users;
        users = getMatchingUser(userEmail, userPassword);
        User user = users.isEmpty() ? null : users.get(0);
        if (user instanceof Student) {
            return userAssignedToClass(userEmail, userPassword);
        }
        if (user == null) {
            UI.getInstance().print("Couldn't find user matching provided email and password.\n");
            return null;
        }
        return user;
    }

    private User userAssignedToClass(String userEmail, char[] userPassword) {
        List<User> users = UserDao.getInstance().findMatching(
                "SELECT * " +
                      "FROM users " +
                      "JOIN classes_students " +
                      "ON id_user = id " +
                      "WHERE email = '" + userEmail + "' AND password = '" + new String(userPassword) + "' AND id_class IN (1,2,3,4);");
        if (users.isEmpty()) {
            UI.getInstance().print("You are not assigned to any class. Ask mentor to assign you to a class.\n");
            return null;
        }
        return users.get(0);
    }

    private List<User> getMatchingUser(String userEmail, char[] userPassword) {
        return UserDao.getInstance().findMatching(
                "SELECT * FROM users WHERE email = '" + userEmail + "' AND password = '" + new String(userPassword) + "';");
    }
}
