package com.codecool.ccms.dao;

import com.codecool.ccms.models.User;

import java.util.List;

interface MentorDAO extends UserDAO{

    List<User> findAllMentors();
    List<User> findMentorsById();
    List<User> findMentorsByName();
}
