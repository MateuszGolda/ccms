package com.codecool.ccms.session;

import com.codecool.ccms.controllers.SessionController;

public class Session {

    public Session() {
        SessionController sessionController = new SessionController();
        sessionController.runMenu();
    }
}
