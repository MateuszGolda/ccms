package com.codecool.ccms.dao;

import com.codecool.ccms.models.Role;
import com.codecool.ccms.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Dao<User> {
    private static UserDao instance;

    private UserDao() {}

    public static UserDao getInstance(){
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public List<User> getMatching(String query) {
        List<User> users = new ArrayList<>();
        connect();

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
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getByField(String column, String value) {
        String query = "SELECT * FROM Users WHERE " + column + " = " + value + ";";
        List<User> users = getMatching(query);
        return users.isEmpty() ? null : users.get(0);
    }

    public void insert(String[] values) {
        String[] columns = { "name", "surname", "password", "email", "id_role" };
        // add '' for text data
        for (int i = 0; i < 5; i++) {
            values[i] = String.format("'%s'", values[i]);
        }
        insert("users", columns, values);
    }

    public void updateById(String id, String column, String newValue) {
        newValue = String.format("'%s'", newValue);
        updateById("users", id, column, newValue);
    }

    public void update(String column, String newValue, String condition) {
        newValue = String.format("'%s'", newValue);
        update("users", column, newValue, condition);
    }

    public void print(String columns, String condition) {
        printFromDB("users", columns, condition);
    }

    @Override
    public List<User> getAll() {
        return getMatching("SELECT * FROM users;");
    }

    @Override
    public void printAll() {
        printFromDB("SELECT * FROM users;");
    }
}
