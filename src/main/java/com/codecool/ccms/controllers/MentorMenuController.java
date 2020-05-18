package com.codecool.ccms.controllers;

import java.util.HashMap;

public class MentorMenuController implements MenuController {
    private boolean isLogin = true;
    private HashMap<Integer, Runnable> mentorMenu;

    @Override
    public String[] gatherContextMenu() {
        return new String[]{"Welcome Mentor Menu:",
                "(1) Display list of students",
                "(2) Add an Assignment",
                "(3) Grade an assignment",
                "(4) Check attendance",
                "(5) Add a Student to a class",
                "(6) Edit student's data",
                "(7) Remove a Student",
                "(8) Logout"};
    }

//    private void mentorPanel() {
//        while(isLogin) {
//            ui.print(gatherContextMenu());
//            int userChoice = io.gatherIntInput("\nEnter a number: ", 1, 8);
//            mentorMenu.get(userChoice).run();
//        }
//    }

    @Override
    public void initializeMenu() {
        mentorMenu = new HashMap<>();
        mentorMenu.put(1, this::displayStudentsList);
        mentorMenu.put(2, this::addAssignment);
        mentorMenu.put(3, this::gradeAssignment);
        mentorMenu.put(4, this::checkAttendance);
        mentorMenu.put(5, this::addStudent);
        mentorMenu.put(6, this::editStudent);
        mentorMenu.put(7, this::removeStudent);
        mentorMenu.put(8, this::logout);
    }

    private void displayStudentsList() {
    }

    private void addAssignment() {
    }

    private void gradeAssignment() {
    }

    private void checkAttendance() {
    }

    private void addStudent() {
    }

    private void editStudent() {
    }

    private void removeStudent() {
    }

    private void logout() {
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }
}
