package com.codecool.ccms.dao;

import com.codecool.ccms.models.Role;
import com.codecool.ccms.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Dao<User> {

    public List<User> getUsers(String query) {
        List<User> users = new ArrayList<>();
        connect();

        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                String surname = results.getString("surname");
                String email = results.getString("email");
                String password = results.getString("password");
                String phone = results.getString("phone");

                // TODO factory pattern
                //if (results.getString("Id_role") == Role.EMPLOYEE.toString()) {
                //    User user = new User(id, email, name, surname, phone);
                //    users.add(customer);
                //}
            }
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void insertUser(String[] values) {
        String[] columns = { "name", "surname", "email", "password", "phone", "Id_role" };

        for (int i = 0; i < 5; i++) {
            values[i] = String.format("'%s'", values[i]);
        }
        insert("Users", columns, values);

    }

    public void updateUser(String id, String column, String newValue) {
        newValue = String.format("'%s'", newValue);
        updateById("Users", id, column, newValue);
    }

    public void print(String columns, String condition) {
        printFromDB("Users", columns, condition);
    }

    @Override
    public List<User> getAll() {
        return getUsers("SELECT * FROM users;");
    }

    @Override
    public void printAll() {
        printFromDB("SELECT * FROM Users;");
    }
}
