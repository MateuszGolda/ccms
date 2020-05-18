package com.codecool.ccms.models;

import com.codecool.ccms.controllers.MenuController;

public class Student extends User{

    public Student(String name, String surname, String eMail, String password, Role role, MenuController menuController) {
        super(name, surname, eMail, password, role, menuController);
    }
}
