package com.codecool.ccms.models;

import com.codecool.ccms.controllers.MentorMenuController;

public class Mentor extends User {
    public Mentor(int id, String name, String surname, String email) {
        super(id, name, surname, email);
    }

    @Override
    protected void displayMainMenu() {

    }
    @Override
    public void setController() {
        setMenuController(new MentorMenuController());
    }
}
