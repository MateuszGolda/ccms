package com.codecool.ccms.dao;

import com.codecool.ccms.models.User;

import java.util.List;

interface EmployeeDAO extends UserDAO{

    List<User> findAllEmployees();
    List<User> findEmployeesById();
    List<User> findEmployeesByName();
}
