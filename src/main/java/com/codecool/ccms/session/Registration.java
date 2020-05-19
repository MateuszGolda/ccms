package com.codecool.ccms.session;

import com.codecool.ccms.dao.UserDao;
import com.codecool.ccms.models.User;
import com.codecool.ccms.ui.UI;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration {
    private final UI ui;

    Registration() {
        ui = UI.getInstance();
        register();
    }

    private void register() {
        String email = ui.gatherInput("Enter your email: ").toLowerCase();
        UserDao userDao = new UserDao();
        List<User> sameEmailUsers = userDao.get("SELECT * FROM Users WHERE email = \"" + email + "\";");
        if (isEmailTaken(sameEmailUsers)) {
            ui.gatherEmptyInput("User with this email already exists");
            return;
        }
        if (!isEmailValid(email)) {
            ui.gatherEmptyInput("Invalid email address");
            return;
        }
        String password = ui.gatherInput("Enter your password: ");
        String name = ui.gatherInput("Enter your name: ");
        String surname = ui.gatherInput("Enter your surname: ");
        String id_role = "1"; // by default new user role is student
        String[] values = {name, surname, password, email, id_role};
        userDao.insert(values);
        ui.gatherEmptyInput("Successfully registered!");
    }

    private boolean isEmailTaken(List<User> sameEmailUsers) {
        return !sameEmailUsers.isEmpty();
    }

    public boolean isEmailValid(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])" +
                "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
