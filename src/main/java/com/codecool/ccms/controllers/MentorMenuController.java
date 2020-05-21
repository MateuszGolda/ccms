package com.codecool.ccms.controllers;

import com.codecool.ccms.dao.AssignmentDao;
import com.codecool.ccms.dao.ClassesStudentsDao;
import com.codecool.ccms.dao.SubmittedAssignmentDao;
import com.codecool.ccms.dao.UserDao;
import com.codecool.ccms.models.SubmittedAssignment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MentorMenuController implements  MenuController{
    private boolean isLogin = true;
    private Map<Integer, Runnable> mentorMenu;

    @Override
    public String[] gatherContextMenu() {
        return new String[]{"Welcome Mentor Menu:",
                "(1) Display list of students",
                "(2) Add an Assignment",
                "(3) Grade an assignment",
                "(4) Check attendance",
                "(5) Add a Student to a class",
                "(6) Edit student's data",
                "(7) Remove a Student",
                "(8) Logout"};
    }

    @Override
    public void runMenu() {
        initializeMenu();
        while(isLogin) {
            ui.print(gatherContextMenu());
            int userChoice = ui.gatherIntInput("\nEnter a number: ", 1, 8);
            mentorMenu.get(userChoice).run();
        }
    }

    @Override
    public void initializeMenu() {
        mentorMenu = new HashMap<>();
        mentorMenu.put(1, this::displayStudentsList);
        mentorMenu.put(2, this::addAssignment);
        mentorMenu.put(3, this::gradeAssignment);
        mentorMenu.put(4, this::checkAttendance);
        mentorMenu.put(5, this::addStudent);
        mentorMenu.put(6, this::editStudent);
        mentorMenu.put(7, this::removeStudent);
        mentorMenu.put(8, this::logout);
    }

    private void displayStudentsList() {
        //ui.printTableFromDB(UserDao.getInstance().resultSetFromQuery("SELECT ")); //todo print table of students to se how they perform
        UserDao.getInstance().print("id, name, surname, email","id_role = 1");
        List<SubmittedAssignment> submittedAssignmentList = SubmittedAssignmentDao.getInstance().findGradedAssignments();
        Map<String, Integer> numberOfGradesMap = new HashMap<>();
        Map<String, Integer> sumOfGradesMap = new HashMap<>();
        Map<String, Float> studentsAverageGradesMap = new HashMap<>();
        for (SubmittedAssignment entry : submittedAssignmentList) {
            String student = String.format("%s %s", entry.getName(), entry.getSurname());
            int grade = entry.getGrade();
            int numberOfGrades = 1;
            if (!studentsAverageGradesMap.containsKey(student)) {
                numberOfGradesMap.put(student, numberOfGrades);
                sumOfGradesMap.put(student, grade);
                studentsAverageGradesMap.put(student,  ((float)grade/(float)numberOfGrades) );
            } else {
                numberOfGradesMap.put(student,(numberOfGradesMap.get(student) + 1));
                sumOfGradesMap.put(student,(sumOfGradesMap.get(student) + grade) );
                studentsAverageGradesMap.put(student, ((float)sumOfGradesMap.get(student)/(float)numberOfGradesMap.get(student)));
            }
        }
        ui.print("(Student) [Średnia Ocen]");
        ui.printMap(studentsAverageGradesMap);
        ui.print("\n");
    }

    private void addAssignment() {
        String[] data = gatherAssignmentData();
        AssignmentDao.getInstance().insert(data);
        ui.gatherEmptyInput("Assignment successfully added!\n");
    }

    private String[] gatherAssignmentData() {
        String title = ui.gatherInput("Enter title for Assignment: ");
        String description = ui.gatherInput("Enter assignment description: ");
        String isAvailable = "1"; //default set as available todo improve this magic number
        return new String[] { title, description, isAvailable };
    }

    private void gradeAssignment() {
        displayAssignmentList();
        String id = String.valueOf(ui.gatherIntInput("Enter id of assignment to grade: "));
        displayUngradedAssignments( id);
        String userID = String.valueOf(ui.gatherIntInput("Enter id_user to grade assignment: "));
        displayConcreteAssignment(id, userID);
        int grade = ui.gatherIntInput("What will be the grade for that assignment?",1, 5);
        String condition =" id_assignment = '" + id + "' AND id_user = '" + userID+"'";
        SubmittedAssignmentDao.getInstance().update("grade", String.valueOf(grade), condition);
        ui.print("Assignment successfully graded\n");
    }

    private void displayConcreteAssignment(String id, String userID) {
        ui.print("Assignment to grade:\n");
        ui.printTableFromDB(AssignmentDao.getInstance().resultSetFromQuery("SELECT id_assignment, id_user, content FROM assignments_users\n" +
                "Where id_assignment = " + id + " and id_user = " + userID + " and grade is null"));
    }

    private void displayUngradedAssignments(String id) {
        ui.print("Ungraded assignments:\n");
        ui.printTableFromDB(AssignmentDao.getInstance().resultSetFromQuery("SELECT id_assignment, id_user, content FROM assignments_users\n" +
                "WHERE id_assignment = "+ id +" AND grade is null"));
    }

    private void displayAssignmentList() {
        AssignmentDao.getInstance().print("*","isAvailable = 1");
    }

    private void checkAttendance() {

    }

    private void addStudent() {
            displayStudentsList();
            String id = String.valueOf(ui.gatherIntInput("Enter id_user to add to a class: "));
            UserDao.getInstance().print("*","id = " + id);
            Map<Integer, String> classList = getClassList();
            ui.printMap(classList);
            int userChoice = ui.gatherIntInput("What class would you assign to that student?",1, classList.size());
            ClassesStudentsDao.getInstance().insert(new String[] {id, String.valueOf(userChoice)});
            ui.print("Student successfully added to class\n");
    }

    private Map<Integer, String> getClassList() {
        Map<Integer, String> classList = new HashMap<>();
        classList.put(1, "ProgBasic");
        classList.put(2, "JavaOOP");
        classList.put(3, "Front-End");
        classList.put(4, "Advance");
        return classList;
    }

    private void editStudent() {
        displayStudentsList();
        String id = String.valueOf(ui.gatherIntInput("Enter id_user to edit student class: "));
        UserDao.getInstance().print("*","id = " + id);
        Map<Integer, String> classList = getClassList();
        ui.printMap(classList);
        int userChoice = ui.gatherIntInput("What class would you assign to that student?",1, classList.size());
        ClassesStudentsDao.getInstance().update("id_class", String.valueOf(userChoice),"id_user = " + id);
        ui.print("Successfully updated student class\n");
    }

    private void removeStudent() {
        displayStudentsList();
        String id = String.valueOf(ui.gatherIntInput("Enter id_user to remove student from class: "));
        ClassesStudentsDao.getInstance().remove(id);
        ui.print("Student successfully removed form class\n");
    }

    private void logout() {
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }
}
