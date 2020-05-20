package com.codecool.ccms.dao;

import com.codecool.ccms.models.User;

interface UserDAO {

    boolean insertUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(User user);
}
