package com.codecool.ccms.dao;

import com.codecool.ccms.models.User;

import java.util.List;

interface ManagerDAO extends UserDAO{

    List<User> findAllManagers();
    List<User> findManagersById();
    List<User> findManagersByName();
}
