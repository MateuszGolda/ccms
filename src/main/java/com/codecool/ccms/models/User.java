package com.codecool.ccms.models;

import com.codecool.ccms.controllers.MenuController;

public abstract class User {
    private String name;
    private String surname;
    private String eMail;
    private String password;
    private Role role;
    private MenuController menuController;

    public void displayMenu(){
        menuController.gatherContextMenu();
    }

    public User(String name, String surname, String eMail, String password, Role role, MenuController menuController){
        

    }

    public void setMenuController(MenuController mc){
        menuController = mc;
    }

}
