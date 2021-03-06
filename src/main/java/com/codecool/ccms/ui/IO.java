package com.codecool.ccms.ui;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

public class IO {
    public final Scanner sc;

    public IO() {
        sc = new Scanner(System.in);
        sc.useDelimiter(System.lineSeparator());
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
            userInput = sc.nextLine();
            if (!userInput.equals("")) {
                validInput = true;
            }
        } while (!validInput);
        return userInput;
    }

    public void gatherEmptyInput(String message) {
        System.out.println(message);
        sc.nextLine();
    }

    public int gatherIntInput(String message, int rangeMin, int rangeMax) {
        System.out.println(message);
        String userInput = "";
        boolean validInput = false;
        while (!validInput) {
            userInput = sc.nextLine();
            validInput = isNumberInRange(userInput, rangeMin, rangeMax);
        }
        return Integer.parseInt(userInput);
    }

    public int gatherIntInput(String message) {
        System.out.println(message);
        String userInput = "";
        boolean validInput = false;
        while (!validInput) {
            userInput = sc.nextLine();
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

    public String readAndHashPassword(String format, Object... args) {
        if (System.console() != null) {
            return BCrypt.hashpw(new String(System.console().readPassword(format, args)), BCrypt.gensalt());
        }
        return BCrypt.hashpw(readLine(format, args), BCrypt.gensalt());
    }

    public char[] readPassword(String format, Object... args) {
        if (System.console() != null) {
            return System.console().readPassword(format, args);
        }
        return readLine(format, args).toCharArray();
    }

    private String readLine(String format, Object... args) {
        System.out.print(String.format(format, args));
        return sc.nextLine();
    }
}
