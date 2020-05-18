package com.codecool.ccms.models;

public class Employee extends User {
    public Employee(int id, String name, String surname, String email) {
        super(id, name, surname, email);
    }

    @Override
    protected void displayMainMenu() {

    }
}
