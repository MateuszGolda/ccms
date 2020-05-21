package com.codecool.ccms.models;

public class SubmittedAssignment {
    private final int id_assignment;
    private final int id_user;
    private final int grade;

    public SubmittedAssignment(int id_assignment, int id_user, int grade){
        this.id_assignment = id_assignment;
        this.id_user = id_user;
        this.grade = grade;
    }

    public int getId_assignment() {
        return id_assignment;
    }

    public int getId_user() {
        return id_user;
    }

    public int getGrade() {
        return grade;
    }
}
