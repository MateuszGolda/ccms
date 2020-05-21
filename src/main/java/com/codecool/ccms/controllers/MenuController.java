package com.codecool.ccms.controllers;

import com.codecool.ccms.ui.UI;

public interface MenuController {
    UI ui = UI.getInstance();

    String[] gatherContextMenu();

    void runMenu();

    void initializeMenu();

}
