package com.codecool.ccms.controllers;

import com.codecool.ccms.dao.UserDao;
import com.codecool.ccms.session.Registration;

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
    public void runMenu() {
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
        String[] data = gatherMentorData();
        UserDao.getInstance().insert(data);
        ui.gatherEmptyInput("Mentor successfully added!");
    }

    private String[] gatherMentorData() {
        String name = ui.gatherInput("Enter mentor name: ");
        String surname = ui.gatherInput("Enter mentor surname: ");
        String password = ui.gatherInput("Enter mentor password: ");
        String email = ui.gatherInput("Enter mentor email: ");
        String mentor_id = "2"; //default id for mentor
        return new String[] { name, surname, password, email, mentor_id };
    }

    private void removeMentor() {
        displayMentorsList();
        String id = ui.gatherInput("Enter id to remove mentor: ");
        UserDao.getInstance().remove("users", id);

    }

    private void editMentor() {
        displayMentorsList();
        String id = ui.gatherInput("Enter id to edit mentor: ");
        UserDao.getInstance().print("*","id = " + id);
        Map<Integer, String> mentorFields = mentorFieldsMap();
        ui.printMap(mentorFields);
        int userChoice = ui.gatherIntInput("What field would you like to change",1, mentorFields.size());
        String newValue = ui.gatherInput("Enter new value for '" + mentorFields.get(userChoice) + "': ");
        UserDao.getInstance().update(mentorFields.get(userChoice), newValue,"id = " + id);

    }
    public Map<Integer, String> mentorFieldsMap() {
        Map<Integer, String> mentorFields = new HashMap<>();
        mentorFields.put(1, "name");
        mentorFields.put(2, "surname");
        mentorFields.put(3, "password");
        mentorFields.put(4, "email");
        mentorFields.put(5, "id_role");
        return mentorFields;
    }

    private void displayMentorsList() {
        UserDao.getInstance().print("*","id_role = 2");

    }

    private void displayStudentsList() {
        //ui.printTableFromDB(UserDao.getInstance().resultSetFromQuery("SELECT ")); //todo print table of students to se how they perform
        UserDao.getInstance().print("*","id_role = 1");
    }

    private void logout() {
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }

}
