package com.codecool.ccms.session;

import com.codecool.ccms.dao.UserDao;
import com.codecool.ccms.models.Student;
import com.codecool.ccms.models.User;
import com.codecool.ccms.ui.UI;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class Login {
    public User loginAttempt(String userEmail, char[] userPassword) {
        List<User> users;
        users = getMatchingUsers(userEmail, userPassword);
        User user = users.isEmpty() ? null : users.get(0);
        if (user instanceof Student) {
            return userAssignedToClass(userEmail);
        }
        if (user == null) {
            UI.getInstance().print("Couldn't find user matching provided email and password.\n");
            return null;
        }
        return user;
    }

    private User userAssignedToClass(String userEmail) {
        List<User> users = UserDao.getInstance().findMatching(
                "SELECT * " +
                      "FROM users " +
                      "JOIN classes_students " +
                      "ON id_user = id " +
                      "WHERE email = '" + userEmail + "' AND id_class IN (1,2,3,4);");
        if (users.isEmpty()) {
            UI.getInstance().print("You are not assigned to any class. Ask mentor to assign you to a class.\n");
            return null;
        }
        return users.get(0);
    }

    private List<User> getMatchingUsers(String userEmail, char[] userPassword) {
        List<User> matchingUsers = UserDao.getInstance().findMatching(
                "SELECT * FROM users WHERE email = '" + userEmail + "';");
        matchingUsers.removeIf(user -> !BCrypt.checkpw(new String(userPassword), user.getPassword()));
        return matchingUsers;
    }
}
