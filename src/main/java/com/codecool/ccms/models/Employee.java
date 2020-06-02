package com.codecool.ccms.models;

import com.codecool.ccms.controllers.EmployeeMenuController;

public class Employee extends User {
    public Employee(int id, String name, String surname, String email, String password) {
        super(id, name, surname, email, password);
    }

    @Override
    protected void displayMainMenu() {

    }

    @Override
    public void setController() {
        setMenuController(new EmployeeMenuController());
    }
}
