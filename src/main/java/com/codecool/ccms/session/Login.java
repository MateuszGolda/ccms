package com.codecool.ccms.session;

import com.codecool.ccms.dao.UserDaoImplementation;
import com.codecool.ccms.models.User;

import java.util.List;

public class Login {
    public User loginAttempt(String userEmail, char[] userPassword) {
        List<User> users;
        users = getMatchingUser(userEmail, userPassword);
        return users.isEmpty() ? null : users.get(0);
    }

    private List<User> getMatchingUser(String userEmail, char[] userPassword) {
        return UserDaoImplementation.getInstance().getMatching(
                "SELECT * FROM Users WHERE email = '" + userEmail + "' AND password = '" + new String(userPassword) + "';");
    }
}
