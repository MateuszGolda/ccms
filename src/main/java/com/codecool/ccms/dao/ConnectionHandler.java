package com.codecool.ccms.dao;

import java.sql.Connection;
import java.sql.Statement;

public interface ConnectionHandler {

    void connect();

    Statement getStatement();

    Connection getConnection();
}
