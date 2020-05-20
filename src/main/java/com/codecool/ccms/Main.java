package com.codecool.ccms;

import com.codecool.ccms.dao.ConnectionToDB;
import com.codecool.ccms.dao.IConnection;
import com.codecool.ccms.session.Session;

/**
 * Main
 */
public class Main {
    public static IConnection iConnection;
    public static void main( String[] args )
    {
        iConnection = new ConnectionToDB("src/main/resources/ccmsDB");
        new Session();
    }
}
