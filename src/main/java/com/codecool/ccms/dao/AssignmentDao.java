package com.codecool.ccms.dao;

import com.codecool.ccms.models.Assignment;

import java.util.List;

public class AssignmentDao extends RelationalDBDao<Assignment> {
    private static AssignmentDao instance;

    private AssignmentDao() {
        super();
    }

    public static AssignmentDao getInstance(){
        if (instance == null) {
            instance = new AssignmentDao();
        }
        return instance;
    }

    @Override
    public List<Assignment> findById(String id) {
        return null;
    }

    @Override
    public List<Assignment> findByName(String name) {
        return null;
    }

    @Override
    public List<Assignment> findMatching(String query) {
        return null;
    }

    @Override
    public List<Assignment> find(String column, String value) {
        return null;
    }

    @Override
    public void insert(String[] values) {
        String[] columns = { "title", "description", "isAvailable" };
        for (int i = 0; i < values.length; i++) {
            values[i] = String.format("'%s'", values[i]);
        }
        insert("assignments", columns, values);
    }

    @Override
    public void updateById(String id, String column, String newValue) {

    }

    @Override
    public void update(String column, String newValue, String condition) {
        newValue = String.format("'%s'", newValue);
        update("assignments", column, newValue, condition);
    }

    @Override
    public void print(String columns, String condition) {
        ui.printTableFromDB(resultSetFromQuery("assignments", columns, condition));
    }

    @Override
    public List<Assignment> findAll(String table) {
        return findMatching("SELECT * FROM " + table + ";");
    }

    @Override
    public void printAll(String table) {

    }
}
