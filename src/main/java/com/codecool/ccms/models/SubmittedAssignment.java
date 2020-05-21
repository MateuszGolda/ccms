package com.codecool.ccms.models;

public class SubmittedAssignment {
    private final int id_assignment;
    private final int id_user;
    private final int grade;
    private final String name;
    private final String surname;

    public SubmittedAssignment(int id_user,String name, String surname, int id_assignment, int grade){
        this.id_assignment = id_assignment;
        this.id_user = id_user;
        this.grade = grade;
        this.name = name;
        this.surname = surname;
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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
