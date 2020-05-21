package com.codecool.ccms.dao;

import com.codecool.ccms.ui.UI;

import java.sql.*;

public class ConnectionToDB implements ConnectionHandler {
    protected Connection connection;
    protected static Statement statement;
    protected final UI ui = UI.getInstance();

    public static String DB_NAME;
    public static String CONNECTION_STRING;

    public ConnectionToDB(String dbName) {
        DB_NAME = dbName;
        CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(CONNECTION_STRING);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.getStackTrace();
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database" + e.getMessage());
        }
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
