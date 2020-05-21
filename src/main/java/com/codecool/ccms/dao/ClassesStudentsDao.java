package com.codecool.ccms.dao;

import java.sql.SQLException;
import java.util.List;

public class ClassesStudentsDao extends RelationalDBDao {
    private static ClassesStudentsDao instance;

    private ClassesStudentsDao() {
        super();
    }

    public static ClassesStudentsDao getInstance(){
        if (instance == null) {
            instance = new ClassesStudentsDao();
        }
        return instance;
    }

    @Override
    public List findById(String id) {
        return null;
    }

    @Override
    public List findByName(String name) {
        return null;
    }

    @Override
    public List findMatching(String query) {
        return null;
    }

    @Override
    public List find(String column, String value) {
        return null;
    }

    @Override
    public void insert(String[] values) {
        String[] columns = { "id_user", "id_class" };
        for (int i = 0; i < values.length; i++) {
            values[i] = String.format("'%s'", values[i]);
        }
        insert("classes_students", columns, values);
    }

    @Override
    public void updateById(String id, String column, String newValue) {

    }

    @Override
    public void update(String column, String newValue, String condition) {
        newValue = String.format("'%s'", newValue);
        update("classes_students", column, newValue, condition);
    }

    @Override
    public void print(String columns, String condition) {

    }

    @Override
    public List findAll(String table) {
        return null;
    }

    @Override
    public void printAll(String table) {

    }

    public void remove(String user_id) {
        String query = String.format("DELETE FROM classes_students WHERE id_user = %s;", user_id);

        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
