package com.codecool.ccms.dao;

import java.sql.*;
import java.util.List;

import com.codecool.ccms.Main;
import com.codecool.ccms.ui.UI;

public abstract class RelationalDBDao<T> implements DAO<T> {
    protected Connection connection;
    protected ConnectionHandler connectionHandler;
    protected Statement statement;
    protected final UI ui;

    public RelationalDBDao() {
        connectionHandler = Main.connectionHandler;
        statement = connectionHandler.getStatement();
        connection = connectionHandler.getConnection();
        ui = UI.getInstance();
    }

    public ResultSet resultSetFromQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet resultSetFromQuery(String table, String columns, String condition) {
        String where = condition.isEmpty() ? "" : "WHERE" + condition;
        String query = String.format("SELECT %s FROM %s %s;", columns, table, where);
        return resultSetFromQuery(query);
    }

    public void updateById(String table, String id, String column, String newValue) {
        String condition = String.format("Id = %s", id);
        update(table, column, newValue, condition);
    }

    public void update(String table, String column, String newValue, String condition) {
        if (column.toLowerCase().equals("id")) {
            System.out.println("You can't change id");
            return;
        }
        String query = String.format("UPDATE %s SET %s = %s WHERE %s;", table, column, newValue, condition);

        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String table, String id) {
        String query = String.format("DELETE FROM %s WHERE Id = %s;", table, id);

        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String table, String[] columns, String[] values) {
        String columnsAsQuery = String.join(",", columns);
        String valuesAsQuery = String.join(",", values);
        String query = String.format("INSERT INTO %s (%s) VALUES (%s);", table, columnsAsQuery, valuesAsQuery);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract List<T> findById(String id);

    public abstract List<T> findByName(String name);

    public abstract List<T> findMatching(String query);

    public abstract List<T> find(String column, String value);

    public abstract void insert(String[] values);

    public abstract void updateById(String id, String column, String newValue);

    public abstract void update(String column, String newValue, String condition);

    public abstract void print(String columns, String condition);

    public abstract List<T> findAll(String table);

    public abstract void printAll(String table);
}
