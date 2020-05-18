package com.codecool.ccms.models;

public class Mentor extends User {
    public Mentor(int id, String name, String surname, String email) {
        super(id, name, surname, email);
    }

    @Override
    protected void displayMainMenu() {

    }
}
