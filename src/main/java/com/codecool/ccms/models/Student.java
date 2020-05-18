package com.codecool.ccms.models;

public class Student extends User {
    public Student(int id, String name, String surname, String email) {
        super(id, name, surname, email);
    }

    @Override
    protected void displayMainMenu() {

    }
}
