package com.codecool.ccms.controllers;

import com.codecool.ccms.models.User;
import com.codecool.ccms.session.Login;
import com.codecool.ccms.session.Registration;

import java.util.HashMap;
import java.util.Map;

public class SessionController implements MenuController{
    private boolean isRunning = true;
    private Map<Integer, Runnable> mainMenu;

    @Override
    public String[] gatherContextMenu() {
        return new String[]{"Welcome to ccms\n"
                            + "(1) Login\n"
                            + "(2) Register\n"
                            + "(3) Exit"};
    }

    @Override
    public void runMenu() {
        initializeMenu();
        while(isRunning) {
            ui.print(gatherContextMenu());
            int userChoice = ui.gatherIntInput("\nEnter a number: ", 1, 3);
            mainMenu.get(userChoice).run();
        }
    }

    @Override
    public void initializeMenu() {
        mainMenu = new HashMap<>();
        mainMenu.put(1, this::login);
        mainMenu.put(2, this::registration);
        mainMenu.put(3, this::exit);
    }

    private void login() {
        User user = logIn(new Login());
        if (user == null) {
            return;
        }
        ui.print("Logged in!");
        user.displayMenu();
    }

    private void registration() {
        new Registration();
    }

    private void exit() {
        isRunning = false;
        System.out.println("\nBye bye\n");
    }

    private User logIn(Login login) {
        User loggedUser;
        String userEmail;

            userEmail = ui.gatherInput("Email: ").toLowerCase();
            char[] userPassword = ui.readPassword("Password: ");
            loggedUser = login.loginAttempt(userEmail, userPassword);
        return loggedUser;
    }
}
