package com.codecool.ccms.controllers;

import com.codecool.ccms.dao.*;
import com.codecool.ccms.models.Presence;
import com.codecool.ccms.models.SubmittedAssignment;
import com.codecool.ccms.models.User;
import com.jakewharton.fliptables.FlipTable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MentorMenuController implements MenuController {
    private boolean isLogin = true;
    private Map<Integer, Runnable> mentorMenu;

    private static String[][] mapToArray(Map<String, Float> map) {
        String[][] data = new String[map.size()][2];
        int i = 0;
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            data[i][0] = entry.getKey();
            data[i++][1] = entry.getValue().toString();
        }
        return data;
    }

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
        while (isLogin) {
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
        //UserDao.getInstance().print("id, name, surname, email","id_role = 1"); // general list of students
        displayStudentsWithClass();
        displayStudentsWithoutClass();
        displayStudentsGrades();
    }

    private void displayStudentsGrades() { //todo refactor this method
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
                studentsAverageGradesMap.put(student, ((float) grade / (float) numberOfGrades));
            } else {
                numberOfGradesMap.put(student, (numberOfGradesMap.get(student) + 1));
                sumOfGradesMap.put(student, (sumOfGradesMap.get(student) + grade));
                studentsAverageGradesMap.put(student, ((float) sumOfGradesMap.get(student) / (float) numberOfGradesMap.get(student)));
            }
        }
        String[] header = {"Student", "Grades average"};
        String[][] data = mapToArray(studentsAverageGradesMap);
        ui.print("Students performance:\n");
        ui.print(FlipTable.of(header, data));
    }

    private String[][] mapToArray2(Map<String, String> map) {
        String[][] data = new String[map.size()][2];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            data[i][0] = entry.getKey();
            data[i++][1] = entry.getValue();
        }
        return data;
    }

    private void addAssignment() {
        displayAssignmentList();
        String[] data = gatherAssignmentData();
        AssignmentDao.getInstance().insert(data);
        ui.gatherEmptyInput("Assignment successfully added!\n");
    }

    private String[] gatherAssignmentData() {
        String title = ui.gatherInput("Enter title for new Assignment: ");
        String description = ui.gatherInput("Enter description for new Assignment: ");
        String isAvailable = "1"; //default set as available todo improve this magic number
        return new String[]{title, description, isAvailable};
    }

    private void gradeAssignment() {
        displayAssignmentList();
        String id = String.valueOf(ui.gatherIntInput("Enter id of assignment to grade: "));
        displayUngradedAssignments(id);
        String userID = String.valueOf(ui.gatherIntInput("Enter id_user to grade assignment: "));
        displayConcreteAssignment(id, userID);
        int grade = ui.gatherIntInput("What will be the grade for that assignment?", 1, 5);
        String condition = " id_assignment = '" + id + "' AND id_user = '" + userID + "'";
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
                "WHERE id_assignment = " + id + " AND grade is null"));
    }

    private void displayAssignmentList() {
        ui.print("Current Available Assignments for Students: ");
        AssignmentDao.getInstance().print("*", "isAvailable = 1");
    }

    private void checkAttendance() {
        if (checkIfTodayAlreadyAttendanceChecked()) {
            ui.print("Today: " + getTodayDate() + " presence has been already checked.");
        } else {
            ui.print("Presence not checked today\n");
            AttendanceDayDAO.getInstance().insertDay(getTodayDate());
            boolean isCheckingInProgress = true;
            while (isCheckingInProgress) {
                displayStudentsWithClass();
                String id = String.valueOf(ui.gatherIntInput("Enter id_user to set presence status:\nEnter '0' to end checking attendance."));
                if (!id.trim().equals("0")) {
                    if (!checkIfStudentPresenceStatusAlreadyAssigned(id)) {
                        insertStudentPresenceStatusToDB(id);
                    } else {
                        ui.print("You have already set Presence Status for that student\n");
                        //todo Would you like to edit status for that student?
                    }
                } else {
                    isCheckingInProgress = false;
                }
            }
            ui.print("Checking presence ended\n");
            displayPresencePercentageForStudents();
        }
    }

    private void displayPresencePercentageForStudents() {
        List<User> studentsList = getAllStudentsAssignedToClasses();
        Map<String, String> studentsPresence = new HashMap<>();
        int allDaysWhenPresenceWasChecked = getNumberOfDaysWhenPresenceWasChecked();
        for (User student : studentsList) {
            int noAbsentDays = countAllAbsentDays(student.getId());
            double percentageOfPresence = ((allDaysWhenPresenceWasChecked - noAbsentDays) / (double) allDaysWhenPresenceWasChecked) * 100;
            String studentName = String.format("%s %s", student.getName(), student.getSurname());
            studentsPresence.put(studentName, String.format("%.2f%%", percentageOfPresence));
        }
        String[] header = {"Student", "Presence"};
        String[][] data = mapToArray2(studentsPresence);
        ui.print("Students presence:\n");
        ui.print(FlipTable.of(header, data));
    }

    private List<User> getAllStudentsAssignedToClasses() {
        String query = "SELECT u.id, u.name, surname, email, password, id_role, c.name as class FROM users u\n" +
                "join classes_students cs on u.id = cs.id_user\n" +
                "join classes c on c.id = cs.id_class";
        return UserDao.getInstance().findMatching(query);
    }

    private int getNumberOfDaysWhenPresenceWasChecked() {
        String query = "SELECT * FROM attendance_days";
        return AttendanceDayDAO.getInstance().getNumberOfRecords(query);
    }

    private int countAllAbsentDays(int userID) {
        String query = "SELECT * FROM attendances a\n" +
                "join presence_status ps on id_presence = ps.id\n" +
                "where id_user = " + userID + " and ps.name = 'ABSENT'";
        return AttendanceDayDAO.getInstance().getNumberOfRecords(query);
    }

    private boolean checkIfStudentPresenceStatusAlreadyAssigned(String id) {
        String query = String.format("SELECT * FROM attendances WHERE id_user = '%s' and id_attendance_day = '%s'", id, getDateIDByDate(getTodayDate()));
        return !AttendanceDayDAO.getInstance().getDays(query, "id_attendance_day").isEmpty();
    }

    private void insertStudentPresenceStatusToDB(String id) {
        displayStudentByID(id);
        ui.print("What presence status assign to above student?");
        ui.printMap(getPresenceStatuses());
        int userInput = ui.gatherIntInput("Enter choice: ", 1, getPresenceStatuses().size());
        Presence presenceStatus = getPresenceStatuses().get(userInput);
        delegateToAction(presenceStatus, id);
    }

    private void delegateToAction(Presence presenceStatus, String student_id) {
        if (presenceStatus == Presence.PRESENT) {
            //todo
            // check if for id_user, today date is any record in attendances table,
            // if not do nothing, if is, remove that record
        } else {
            String presence = String.valueOf(presenceStatus.getValue());
            String date = getDateIDByDate(getTodayDate());
            AttendanceDayDAO.getInstance().insert(new String[]{student_id, date, presence});
        }
    }

    private String getDateIDByDate(String todayDate) {
        String query = String.format("SELECT * FROM attendance_days WHERE date = '%s'", todayDate);
        return AttendanceDayDAO.getInstance().getDayID(query);
    }

    private Map<Integer, Presence> getPresenceStatuses() {
        Map<Integer, Presence> classList = new HashMap<>();
        classList.put(1, Presence.ABSENT);
        classList.put(2, Presence.LATE);
        classList.put(3, Presence.EXCUSED);
        classList.put(4, Presence.PRESENT);
        return classList;
    }

    private boolean checkIfTodayAlreadyAttendanceChecked() {
        return checkIfTodayInDB(getTodayDate());
    }

    private String getTodayDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

    private boolean checkIfTodayInDB(String today) {
        String query = String.format("SELECT * FROM attendance_days WHERE date = '%s'", today);
        return !AttendanceDayDAO.getInstance().getDays(query, "date").isEmpty();
    }

    private void addStudent() {
        displayStudentsWithoutClass();
        String id = String.valueOf(ui.gatherIntInput("Enter id_user to add to a class: "));
        UserDao.getInstance().print("*", "id = " + id);
        Map<Integer, String> classList = getClassList();
        ui.printMap(classList);
        int userChoice = ui.gatherIntInput("What class would you assign to that student?", 1, classList.size());
        ClassesStudentsDao.getInstance().insert(new String[]{id, String.valueOf(userChoice)});
        ui.print("Student successfully added to class\n");
    }

    private void displayStudentsWithoutClass() {
        ui.print("Students without assigned class :\n");
        ui.printTableFromDB(AssignmentDao.getInstance().resultSetFromQuery("SELECT id, name, surname, email FROM users\n" +
                "left join classes_students on id=id_user\n" +
                "where id_role = 1 and id_class is null"));
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
        displayStudentsWithClass();
        String id = String.valueOf(ui.gatherIntInput("Enter id_user to edit student class: "));
        displayStudentByID(id);
        Map<Integer, String> classList = getClassList();
        ui.printMap(classList);
        int userChoice = ui.gatherIntInput("What class would you assign to that student?", 1, classList.size());
        ClassesStudentsDao.getInstance().update("id_class", String.valueOf(userChoice), "id_user = " + id);
        ui.print("Successfully updated student class\n");
    }

    private void displayStudentByID(String id) {
        ui.printTableFromDB(AssignmentDao.getInstance().resultSetFromQuery("SELECT u.id, u.name, surname, email, c.name as \"class\" FROM users u\n" +
                "join classes_students cs on u.id = cs.id_user\n" +
                "join classes c on c.id = cs.id_class where u.id =" + id));
    }

    private void displayStudentsWithClass() {
        ui.print("Students with assigned class :\n");
        ui.printTableFromDB(AssignmentDao.getInstance().resultSetFromQuery("SELECT u.id, u.name, surname, email, c.name as \"class\" FROM users u\n" +
                "join classes_students cs on u.id = cs.id_user\n" +
                "join classes c on c.id = cs.id_class"));
    }

    private void removeStudent() {
        displayStudentsWithClass();
        String id = String.valueOf(ui.gatherIntInput("Enter id_user to remove student from class: "));
        ClassesStudentsDao.getInstance().remove(id);
        ui.print("Student successfully removed from class\n");
    }

    private void logout() {
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }
}
