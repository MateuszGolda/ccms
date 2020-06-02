package com.codecool.ccms.models;

import com.codecool.ccms.controllers.MentorMenuController;

public class Mentor extends User {
    public Mentor(int id, String name, String surname, String email, String password) {
        super(id, name, surname, email, password);
    }

    @Override
    protected void displayMainMenu() {

    }
    @Override
    public void setController() {
        setMenuController(new MentorMenuController());
    }
}
