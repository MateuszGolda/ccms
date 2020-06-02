package com.codecool.ccms.models;

import com.codecool.ccms.controllers.ManagerMenuController;

public class Manager extends User {
    public Manager(int id, String name, String surname, String email, String password) {
        super(id, name, surname, email, password);
    }

    @Override
    protected void displayMainMenu() {

    }
    @Override
    public void setController() {
        setMenuController(new ManagerMenuController());
    }
}
