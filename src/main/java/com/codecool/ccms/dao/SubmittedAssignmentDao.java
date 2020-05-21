package com.codecool.ccms.dao;

import com.codecool.ccms.models.SubmittedAssignment;

import java.util.List;

public class SubmittedAssignmentDao extends RelationalDBDao<SubmittedAssignment> {

    private static SubmittedAssignmentDao instance;

    private SubmittedAssignmentDao() {
        super();
    }

    public static SubmittedAssignmentDao getInstance(){
        if (instance == null) {
            instance = new SubmittedAssignmentDao();
        }
        return instance;
    }

    @Override
    public List<SubmittedAssignment> findById(String id) {
        return null;
    }

    @Override
    public List<SubmittedAssignment> findByName(String name) {
        return null;
    }

    @Override
    public List<SubmittedAssignment> findMatching(String query) {
        return null;
    }

    @Override
    public List<SubmittedAssignment> find(String column, String value) {
        return null;
    }

    @Override
    public void insert(String[] values) {
        String[] columns = { "id_assignment", "id_user", "content" };
        for (int i = 0; i < values.length; i++) {
            values[i] = String.format("'%s'", values[i]);
        }
        insert("assignments_users", columns, values);
    }

    @Override
    public void updateById(String id, String column, String newValue) {

    }

    @Override
    public void update(String column, String newValue, String condition) {
        newValue = String.format("'%s'", newValue);
        update("assignments_users", column, newValue, condition);
    }

    @Override
    public void print(String columns, String condition) {

    }

    @Override
    public List<SubmittedAssignment> findAll(String table) {
        return null;
    }

    @Override
    public void printAll(String table) {

    }
}
