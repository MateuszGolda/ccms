package com.codecool.ccms.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class IO {
    public final Scanner scan;

    public IO() {
        scan = new Scanner(System.in);
        scan.useDelimiter(System.lineSeparator());
    }

    public String gatherInput(String title) {
        System.out.println(title);
        boolean validInput = true;
        String userInput;
        do {
            if (!validInput) {
                System.out.println("Your input must contain at least one character. Enter again: ");
            }
            validInput = false;
            userInput = scan.next();
            if (!userInput.equals("")) {
                validInput = true;
            }
        } while (!validInput);
        return userInput;
    }

    public void gatherEmptyInput(String message) {
        System.out.println(message);
        scan.next();
    }

    public int gatherIntInput(String message, int rangeMin, int rangeMax) {
        System.out.println(message);
        String userInput = "";
        boolean validInput = false;
        while (!validInput) {
            userInput = scan.next();
            validInput = isNumberInRange(userInput, rangeMin, rangeMax);
        }
        return Integer.parseInt(userInput);
    }

    public int gatherIntInput(String message) {
        System.out.println(message);
        String userInput = "";
        boolean validInput = false;
        while (!validInput) {
            userInput = scan.next();
            validInput = isInputInt(userInput);
        }
        return Integer.parseInt(userInput);
    }

    private boolean isInputInt(String userInput) {
        if (!userInput.equals("")) {
            if (userInput.matches("^[0-9]*$")) {
                return true;
            }
        }
        System.out.println("Invalid input, please try again: ");
        return false;
    }

    private boolean isNumberInRange(String userInput, int rangeMin, int rangeMax) {
        int userInt;
        if (!userInput.equals("")) {
            if (userInput.matches("^[0-9]*$")) {
                userInt = Integer.parseInt(userInput);
                return userInt >= rangeMin && userInt <= rangeMax;
            }
        }
        System.out.println("Invalid input, please try again: ");
        return false;
    }

    public char[] readPassword(String format, Object... args)
            throws IOException {
        if (System.console() != null)
            return System.console().readPassword(format, args);
        return this.readLine(format, args).toCharArray();
    }

    private String readLine(String format, Object... args) throws IOException {
        if (System.console() != null) {
            return System.console().readLine(format, args);
        }
        System.out.print(String.format(format, args));
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        return reader.readLine();
    }
}
