package com.codecool.ccms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.codecool.ccms.ui.UI;

public abstract class Dao<T> {
    protected Connection connection;
    protected Statement statement;
    protected final UI ui = UI.getInstance();

    public static final String DB_NAME = "src/main/resources/ccmsDB";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

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

    protected void updateById(String table, String id, String column, String newValue) {
        String condition = String.format("Id = %s", id);
        update(table, column, newValue, condition);
    }

    protected void update(String table, String column, String newValue, String condition) {
        if (column.toLowerCase().equals("id")) {
            System.out.println("You can't change id");
            return;
        }
        String query = String.format("UPDATE %s SET %s = %s WHERE %s;", table, column, newValue, condition);

        connect();
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String table, String id) {
        String query = String.format("DELETE FROM %s WHERE Id = %s;", table, id);

        connect();
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void insert(String table, String[] columns, String[] values) {
        String columnsAsQuery = String.join(",", columns);
        String valuesAsQuery = String.join(",", values);
        String query = String.format("INSERT INTO %s (%s) VALUES (%s);", table, columnsAsQuery, valuesAsQuery);
        connect();
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract List<T> getAll();

    public abstract void printAll();
}
