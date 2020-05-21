package com.codecool.ccms.dao;

import com.codecool.ccms.Main;
import com.codecool.ccms.models.Role;
import com.codecool.ccms.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends RelationalDBDao<User> {
    private static UserDao instance;

    private UserDao() {
        super();
    }

    public static UserDao getInstance(){
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public List<User> findMatching(String query) {
        List<User> users = new ArrayList<>();

        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                String surname = results.getString("surname");
                String email = results.getString("email");

                Role role = Role.values()[results.getInt("id_role") - 1];
                User user = UserFactory.getUser(role, id, name, surname, email);
                users.add(user);
            }
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public List<User> find(String column, String value) {
        String query = "SELECT * FROM Users WHERE " + column + " = " + value + ";";
        return findMatching(query);
    }

    @Override
    public void insert(String[] values) {
        String[] columns = { "name", "surname", "password", "email", "id_role" };
        // add '' for text data
        for (int i = 0; i < 5; i++) {
            values[i] = String.format("'%s'", values[i]);
        }
        insert("users", columns, values);
    }

    @Override
    public void updateById(String id, String column, String newValue) {
        newValue = String.format("'%s'", newValue);
        updateById("users", id, column, newValue);
    }

    @Override
    public void update(String column, String newValue, String condition) {
        newValue = String.format("'%s'", newValue);
        update("users", column, newValue, condition);
    }

    @Override
    public void print(String columns, String condition) {
        ui.printTableFromDB(resultSetFromQuery("users", columns, condition));
    }

    @Override
    public List<User> findAll(String table) {
        return findMatching("SELECT * FROM " + table + ";");
    }

    @Override
    public void printAll(String table) {
        ui.printTableFromDB(resultSetFromQuery("SELECT * FROM " + table + ";"));
    }

    @Override
    public List<User> findById(String id) {
        return null;
    }

    @Override
    public List<User> findByName(String name) {
        return null;
    }
}
