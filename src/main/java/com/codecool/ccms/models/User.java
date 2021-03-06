package com.codecool.ccms.models;

import com.codecool.ccms.controllers.MenuController;

public abstract class User {
    private final int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private MenuController menuController;

    User(int id, String name, String surname, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        setController();
    }

    protected abstract void displayMainMenu();

    public void setMenuController(MenuController mc) {
        menuController = mc;
    }

    public abstract void setController();

    public void displayMenu() {
        menuController.runMenu();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
