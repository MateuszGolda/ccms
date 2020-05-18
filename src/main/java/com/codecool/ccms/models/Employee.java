package com.codecool.ccms.models;

import com.codecool.ccms.controllers.EmployeeMenuController;

public class Employee extends User {
    public Employee(int id, String name, String surname, String email) {
        super(id, name, surname, email);
    }

    @Override
    protected void displayMainMenu() {

    }

    @Override
    public void setController() {
        setMenuController(new EmployeeMenuController());
    }
}
