package com.codecool.ccms.controllers;

import com.codecool.ccms.dao.UserDao;

import java.util.HashMap;
import java.util.Map;

public class EmployeeMenuController implements  MenuController {
    private boolean isLogin = true;
    private Map<Integer, Runnable> employeeMenu;

    @Override
    public String[] gatherContextMenu() {
        return new String[]{"Welcome Employee Menu:",
                "(1) Display list of students",
                "(2) Logout"};
    }

    @Override
    public void runMenu() {
        initializeMenu();
        while(isLogin) {
            ui.print(gatherContextMenu());
            int userChoice = ui.gatherIntInput("\nEnter a number: ", 1, 2);
            employeeMenu.get(userChoice).run();
        }
    }

    @Override
    public void initializeMenu() {
        employeeMenu = new HashMap<>();
        employeeMenu.put(1, this::displayStudentsList);
        employeeMenu.put(2, this::logout);
    }

    private void displayStudentsList() {
        UserDao.getInstance().print("id, name, surname, email","id_role = 1");
    }

    private void logout() {
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }
}
