package com.codecool.ccms;

import com.codecool.ccms.dao.ConnectionHandler;
import com.codecool.ccms.dao.ConnectionToDB;
import com.codecool.ccms.session.Session;

import java.sql.SQLException;

/**
 * Main
 */
public class Main {
    public static ConnectionHandler connectionHandler;

    public static void main(String[] args) throws SQLException {
        try {
            connectionHandler = new ConnectionToDB("src/main/resources/ccmsDB");
            connectionHandler.connect();
            new Session();
        } finally {
            connectionHandler.getStatement().close();
            connectionHandler.getConnection().close();
        }
    }
}
