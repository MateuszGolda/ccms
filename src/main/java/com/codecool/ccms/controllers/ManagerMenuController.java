package com.codecool.ccms.controllers;

import java.util.HashMap;
import java.util.Map;

public class ManagerMenuController implements  MenuController{
    private boolean isLogin = true;
    private Map<Integer, Runnable> managerMenu;


    @Override
    public String[] gatherContextMenu() {
        return new String[]{"Welcome Manager Menu:",
                "(1) Add a Mentor",
                "(2) Remove a Mentor",
                "(3) Edit mentors data",
                "(4) Display list of mentors",
                "(5) Display list of students",
                "(6) Logout"};
    }

    @Override
    public void displayPanel() {
        initializeMenu();
        while(isLogin) {
            ui.print(gatherContextMenu());
            int userChoice = ui.gatherIntInput("\nEnter a number: ", 1, 6);
            managerMenu.get(userChoice).run();
        }
    }

    @Override
    public void initializeMenu() {
        managerMenu = new HashMap<>();
        managerMenu.put(1, this::addMentor);
        managerMenu.put(2, this::removeMentor);
        managerMenu.put(3, this::editMentor);
        managerMenu.put(4, this::displayMentorsList);
        managerMenu.put(5, this::displayStudentsList);
        managerMenu.put(6, this::logout);
    }

    private void addMentor() {

    }

    private void removeMentor() {

    }

    private void editMentor() {

    }

    private void displayMentorsList() {

    }

    private void displayStudentsList() {

    }

    private void logout() {
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }

}
