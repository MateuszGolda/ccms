package com.codecool.ccms.dao;

import com.codecool.ccms.models.User;

import java.util.List;

interface StudentDAO extends UserDAO{

    List<User> findAllStudents();
    List<User> findStudentsById();
    List<User> findStudentsByName();
}
