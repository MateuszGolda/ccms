package com.codecool.ccms.dao;

import java.sql.ResultSet;
import java.util.List;

interface DAO<T> {

    List<T> findById(String id);

    List<T> findByName(String name);

    List<T> findMatching(String query);

    List<T> find(String column, String value);

    void insert(String[] values);

    void updateById(String id, String column, String newValue);

    void update(String column, String newValue, String condition);

    void print(String columns, String condition);

    ResultSet resultSetFromQuery(String query);

    ResultSet resultSetFromQuery(String table, String columns, String condition);

    void remove(String table, String id);

    List<T> findAll(String table);

    void printAll(String table);

    void updateById(String table, String id, String column, String newValue);

    void update(String table, String column, String newValue, String condition);

    void insert(String table, String[] columns, String[] values);
}