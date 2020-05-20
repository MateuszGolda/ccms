package com.codecool.ccms.dao;

import com.codecool.ccms.ui.UI;

import java.sql.*;

public class ConnectionToDB implements IConnection{
    protected Connection connection;
    protected Statement statement;
    protected final UI ui = UI.getInstance();

    public static String DB_NAME; // = "src/main/resources/ccmsDB";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    public ConnectionToDB(String dbName) {
        DB_NAME = dbName;
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

    public void printFromDB(String query) {
        connect();
        try {
            ResultSet results = statement.executeQuery(query);
            ui.printTableFromDB(results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printFromDB(String table, String columns, String condition) {
        String where = condition.isEmpty() ? "" : "WHERE" + condition;
        String query = String.format("SELECT %s FROM %s %s;", columns, table, where);
        printFromDB(query);
    }
}
