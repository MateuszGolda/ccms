package com.codecool.ccms.controllers;

import com.codecool.ccms.dao.AssignmentDao;
import com.codecool.ccms.dao.UserDao;

import java.util.HashMap;
import java.util.Map;

public class MentorMenuController implements  MenuController{
    private boolean isLogin = true;
    private Map<Integer, Runnable> mentorMenu;

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

    @Override
    public void runMenu() {
        initializeMenu();
        while(isLogin) {
            ui.print(gatherContextMenu());
            int userChoice = ui.gatherIntInput("\nEnter a number: ", 1, 8);
            mentorMenu.get(userChoice).run();
        }
    }

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
        //ui.printTableFromDB(UserDao.getInstance().resultSetFromQuery("SELECT ")); //todo print table of students to se how they perform
        UserDao.getInstance().print("id, name, surname, email","id_role = 1");
    }

    private void addAssignment() {
        String[] data = gatherAssignmentData();
        AssignmentDao.getInstance().insert(data);
        ui.gatherEmptyInput("Assignment successfully added!");
    }

    private String[] gatherAssignmentData() {
        String title = ui.gatherInput("Enter title for Assignment: ");
        String description = ui.gatherInput("Enter assignment description: ");
        String isAvailable = "1"; //default set as available todo improve this magic number
        return new String[] { title, description, isAvailable };
    }

    private void gradeAssignment() {

    }

    private void checkAttendance() {
    }

    private void addStudent() {

//        String[] values = { name, surname, password, email, id_role };
//        UserDao.getInstance().insert(values);
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
