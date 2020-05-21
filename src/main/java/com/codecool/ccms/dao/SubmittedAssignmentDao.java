package com.codecool.ccms.dao;

import com.codecool.ccms.models.SubmittedAssignment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        List<SubmittedAssignment> assignments = new ArrayList<>();

        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int id_user = results.getInt("id");
                String name = results.getString("name");
                String surname = results.getString("surname");
                int id_assignments = results.getInt("id_assignment");
                int grade = results.getInt("grade");

                SubmittedAssignment submittedAssignment = new SubmittedAssignment(id_user, name, surname, id_assignments, grade);
                assignments.add(submittedAssignment);
            }
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignments;
    }

    @Override
    public List<SubmittedAssignment> find(String column, String value) {
        String query = "SELECT * FROM assignments_users WHERE " + column + " = " + value + ";";
        return findMatching(query);
    }

    public List<SubmittedAssignment> findGradedAssignments() {
        String query = "SELECT id, name, surname, id_assignment, grade FROM assignments_users join users on id_user = id WHERE grade in (1,2,3,4,5)";
        return findMatching(query);
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
