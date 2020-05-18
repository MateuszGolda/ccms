package com.codecool.ccms.ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.jakewharton.fliptables.FlipTableConverters;

public class UI {
    private final IO io;

    public UI() {
        io = new IO();
    }

    public void displayLoginOrRegistrationMenu() {
        print(new String[] {"Welcome to ccms",
                             "(1) Login",
                             "(2) Register",
                             "(2) Exit"});
    }

    public <T> void printTable(Iterable<T> rows, Class<T> rowType) {
        System.out.println(FlipTableConverters.fromIterable(rows, rowType));
    }

    public void printTableFromDB(ResultSet resultSet) throws SQLException {
        System.out.println(FlipTableConverters.fromResultSet(resultSet));
    }

    public void print(String toPrint) {
        System.out.println(toPrint);
    }

    public void print(String[] toPrint) {
        for (String string : toPrint) {
            System.out.println(string);
        }
    }

    public void gatherEmptyInput(String message) {
        io.gatherEmptyInput(message);
    }

    public String gatherInput(String message) {
        return io.gatherInput(message);
    }

    public int gatherIntInput(String message, int rangeMin, int rangeMax) {
        return io.gatherIntInput(message, rangeMin, rangeMax);
    }

    public int gatherIntInput(String message) {
        return io.gatherIntInput(message);
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
}
