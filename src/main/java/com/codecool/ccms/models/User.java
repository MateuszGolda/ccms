package com.codecool.ccms.models;

public abstract class User {
    private final int id;
    private String name;
    private String surname;
    private String email;

    User(int id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    protected abstract void displayMainMenu();


}
