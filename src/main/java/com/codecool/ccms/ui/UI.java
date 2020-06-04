package com.codecool.ccms.ui;

import com.jakewharton.fliptables.FlipTableConverters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UI {
    private static UI instance;
    private final IO io;

    private UI() {
        io = new IO();
    }

    public static UI getInstance() {
        if (instance == null) {
            instance = new UI();
        }
        return instance;
    }

    public <T> void printTable(Iterable<T> rows, Class<T> rowType) {
        System.out.println(FlipTableConverters.fromIterable(rows, rowType));
    }

    public void printTableFromDB(ResultSet resultSet) {
        try {
            System.out.println(FlipTableConverters.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void print(String toPrint) {
        System.out.println(toPrint);
    }

    public void print(String[] toPrint) {
        for (String string : toPrint) {
            System.out.println(string);
        }
    }

    public <K, V> void printMap(Map<K, V> map) {
        map.forEach((k, v) -> System.out.println("(" + k + ") " + v));
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

    public String readAndHashPassword(String format, Object... args) {
        return io.readAndHashPassword(format, args);
    }

    public char[] readPassword(String format, Object... args) {
        return io.readPassword(format, args);
    }
}
