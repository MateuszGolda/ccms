package com.codecool.ccms.models;

public class Manager extends User {
    public Manager(int id, String name, String surname, String email) {
        super(id, name, surname, email);
    }

    @Override
    protected void displayMainMenu() {

    }
}
