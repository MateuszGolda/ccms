package com.codecool.ccms.dao;

import com.codecool.ccms.models.*;

public class UserFactory {
    public static User getUser(Role role, int id, String name, String surname, String email){
        if (Role.STUDENT.equals(role)) return new Student(id, name, surname, email);
        if (Role.MENTOR.equals(role)) return new Mentor(id, name, surname, email);
        if (Role.MANAGER.equals(role)) return new Manager(id, name, surname, email);
        if (Role.EMPLOYEE.equals(role)) return new Employee(id, name, surname, email);

        return null;
    }
}
