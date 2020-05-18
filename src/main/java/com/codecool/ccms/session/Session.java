package com.codecool.ccms.session;

import com.codecool.ccms.models.User;
import com.codecool.ccms.ui.UI;

public class Session {
        private final UI ui;

        public Session() {
            ui = UI.getInstance();
            askIfLoginOrRegistration();
            User user = logIn();
            //setMenuController(user);
            //mainMenuChoice(user);
        }

    private void askIfLoginOrRegistration() {
        boolean registered = false;
        do {
            ui.displayLoginOrRegistrationMenu();
            String input = ui.gatherInput("What to do?: ");
            if (input.equals("2")) {
                new Registration();
            } else if (input.equals("1")) {
                registered = true;
            }
        } while (!registered);
    }

    private User logIn() {
        User loggedUser;
        String userEmail;
        Login login = new Login();
        do {
            userEmail = ui.gatherInput("Email: ").toLowerCase();
            char[] userPassword = ui.gatherInput("Password: ").toCharArray();
            loggedUser = login.loginAttempt(userEmail, userPassword);
        } while (loggedUser == null);
        ui.print("Logged in");
        return loggedUser;
    }
}
