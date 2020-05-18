package com.codecool.ccms.models;

import com.codecool.ccms.controllers.ManagerMenuController;

public class Manager extends User {
    public Manager(int id, String name, String surname, String email) {
        super(id, name, surname, email);
    }

    @Override
    protected void displayMainMenu() {

    }
    @Override
    public void setController() {
        setMenuController(new ManagerMenuController());
    }
}
