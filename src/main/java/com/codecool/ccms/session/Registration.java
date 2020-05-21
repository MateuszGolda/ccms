package com.codecool.ccms.session;

import com.codecool.ccms.dao.UserDao;
import com.codecool.ccms.ui.UI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration {
    private final UI ui;

    public Registration() {
        ui = UI.getInstance();
        String email = ui.gatherInput("Enter your email: ").toLowerCase();
        var validEmail = isEmailAllowed(email);
        if (validEmail) {
            register(email);
        }
    }

    private boolean isEmailAllowed(String email) {
        boolean isEmailAvailable = UserDao.getInstance().find("email", "'" + email + "'").isEmpty();
        if (!isEmailAvailable) {
            ui.gatherEmptyInput("User with this email already exists");
            return false;
        }
        if (!isEmailValid(email)) {
            ui.gatherEmptyInput("Invalid email address");
            return false;
        }
        return true;
    }

    private void register(String email) {
        String password = ui.gatherInput("Enter your password: ");
        String name = ui.gatherInput("Enter your name: ");
        String surname = ui.gatherInput("Enter your surname: ");
        String id_role = "1"; // by default new user role is student
        String[] values = {name, surname, password, email, id_role};
        UserDao.getInstance().insert(values);
        ui.gatherEmptyInput("Successfully registered!");
    }

    public boolean isEmailValid(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])" +
                "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
