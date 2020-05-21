package com.codecool.ccms.controllers;

import java.util.HashMap;
import java.util.Map;

public class StudentMenuController implements MenuController{
    private boolean isLogin = true;
    private Map<Integer, Runnable> studentMenu;

    @Override
    public String[] gatherContextMenu() {
        return new String[]{"Welcome Student Menu:",
                "(1) View my grades",
                "(2) Submit an Assignment",
                "(3) Logout"};
    }

    @Override
    public void runMenu() {
        initializeMenu();
        while(isLogin) {
            ui.print(gatherContextMenu());
            int userChoice = ui.gatherIntInput("\nEnter a number: ", 1, 3);
            studentMenu.get(userChoice).run();
        }
    }

    @Override
    public void initializeMenu() {
        studentMenu = new HashMap<>();
        studentMenu.put(1, this::displayStudentsGrades);
        studentMenu.put(2, this::submitAssignment);
        studentMenu.put(3, this::logout);
    }

    private void displayStudentsGrades() {

    }

    private void submitAssignment() {

    }

    private void logout() {
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }
}
