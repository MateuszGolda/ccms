package com.codecool.ccms.models;

import com.codecool.ccms.controllers.StudentMenuController;

public class Student extends User {
    public Student(int id, String name, String surname, String email, String password) {
        super(id, name, surname, email, password);
    }

    @Override
    protected void displayMainMenu() {

    }

    @Override
    public void setController() {
        setMenuController(new StudentMenuController());
    }
}
