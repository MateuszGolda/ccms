package com.codecool.ccms.controllers;

import com.codecool.ccms.dao.AssignmentDao;
import com.codecool.ccms.dao.SubmittedAssignmentDao;
import com.codecool.ccms.dao.UserDao;
import com.codecool.ccms.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentMenuController implements MenuController{
    private boolean isLogin = true;
    private Map<Integer, Runnable> studentMenu;

    @Override
    public String[] gatherContextMenu() {
        return new String[]{"Welcome Student Menu:",
                "(1) View my grades",
                "(2) Submit an Assignment",
                "(3) Logout"};
    }

    @Override
    public void runMenu() {
        initializeMenu();
        while(isLogin) {
            ui.print(gatherContextMenu());
            int userChoice = ui.gatherIntInput("\nEnter a number: ", 1, 3);
            studentMenu.get(userChoice).run();
        }
    }

    @Override
    public void initializeMenu() {
        studentMenu = new HashMap<>();
        studentMenu.put(1, this::displayStudentsGrades);
        studentMenu.put(2, this::submitAssignment);
        studentMenu.put(3, this::logout);
    }

    private void displayStudentsGrades() {
        String email = ui.gatherInput("Sensitive data, please enter your email: ");
        List<User> user = UserDao.getInstance().find("email", "'"+email+"'");
        String userID = String.valueOf(user.get(0).getId());
        try {
            ui.printTableFromDB(AssignmentDao.getInstance().resultSetFromQuery("SELECT id, title, description, content, grade FROM assignments\n" +
                    "left join assignments_users on id =id_assignment\n" +
                    "where id_user = "+userID+" and grade is not null"));
        } catch (Exception e) {
            ui.print("You don't have any graded assignments, please check later.\n");
        }
        ui.gatherEmptyInput("Press anything to exit\n");
    }

    private void submitAssignment() {
        String email = ui.gatherInput("Confirm your email: ");
        List<User> user = UserDao.getInstance().find("email", "'"+email+"'");
        String userID = String.valueOf(user.get(0).getId());
        ui.printTableFromDB(AssignmentDao.getInstance().resultSetFromQuery("SELECT id, title, description FROM assignments\n" +
                                                                           "left join assignments_users on id =id_assignment\n" +
                                                                           "where isAvailable = 1 and id_user != "+ userID +" or id_user is null"));
        String assignmentID = ui.gatherInput("Enter id of assignment you want to submit: ");
        String content = ui.gatherInput("Enter content: ");
        String[] data = new String[] { assignmentID, userID, content } ;
        SubmittedAssignmentDao.getInstance().insert(data);


    }

    private void logout() {
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }
}
