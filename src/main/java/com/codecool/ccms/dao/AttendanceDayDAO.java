package com.codecool.ccms.dao;

import com.codecool.ccms.models.Presence;
import com.codecool.ccms.models.AttendanceDay;
import com.codecool.ccms.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceDayDAO extends RelationalDBDao<AttendanceDay> {
    private static AttendanceDayDAO instance;
    private final String selectQuery =
            "SELECT id_user AS 'user id', date, name AS presence " +
            "FROM attendance_days days " +
            "JOIN attendances " +
            "ON days.id = attendances.id_attendance_day " +
            "JOIN presence_status presence " +
            "ON id_presence = presence.id";

    private AttendanceDayDAO() {
        super();
    }

    public static AttendanceDayDAO getInstance(){
        if (instance == null) {
            instance = new AttendanceDayDAO();
        }
        return instance;
    }

    @Override
    public List<AttendanceDay> findMatching(String query) {
        List<AttendanceDay> attendanceDays = new ArrayList<>();

        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Map<User, Presence> studentsPresence = fillStudentsPresenceMap();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime day = LocalDateTime.parse(results.getString("date"), formatter);
                AttendanceDay attendanceDay = new AttendanceDay(studentsPresence, day);
                attendanceDays.add(attendanceDay);
            }
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attendanceDays;
    }

    private Map<User, Presence> fillStudentsPresenceMap() throws SQLException {
        List<User> students = UserDao.getInstance().find("id_role", "1");
        Map<User, Presence> studentsPresence = new HashMap<>();
        for (User student : students) {
            studentsPresence.put(student, getStudentPresence(student));
        }
        return studentsPresence;
    }

    private Presence getStudentPresence(User user) throws SQLException {
        ResultSet results = statement.executeQuery(selectQuery + " WHERE id_user = " + user.getId() + ";");
        String presence = results.getString("presence");
        return Presence.valueOf(presence);
    }

    @Override
    public List<AttendanceDay> find(String column, String value) {
        String query = selectQuery + " WHERE " + column + " = " + value + ";";
        return findMatching(query);
    }

    @Override
    public void insert(String[] values) {
        String[] columns = { "id_user", "id_attendance_day", "id_presence" };
        insert("attendances", columns, values);
    }

    public void insertDay(String[] day) {
        String[] columns = { "day" };
        insert("attendance_days", columns, day);
    }

    @Override
    public void updateById(String dayId, String column, String newValue) {
        newValue = String.format("'%s'", newValue);
        updateById("attendance_days", dayId, column, newValue);
    }

    @Override
    public void update(String column, String newValue, String condition) {
        newValue = String.format("'%s'", newValue);
        update("attendances", column, newValue, condition);
    }

    @Override
    public void print(String columns, String condition) {
        String table =
                "attendance_days days " +
                "JOIN attendances " +
                "ON days.id = attendances.id_attendance_day " +
                "JOIN presence_status presence " +
                "ON id_presence = presence.id";
        ui.printTableFromDB(resultSetFromQuery(table, columns, condition));
    }

    @Override
    public List<AttendanceDay> findAll(String table) {
        return findMatching(selectQuery + ";");
    }

    @Override
    public void printAll(String table) {
        ui.printTableFromDB(resultSetFromQuery(selectQuery + ";"));
    }

    @Override
    public List<AttendanceDay> findById(String id) {
        String query = String.format("%s WHERE id = %s;", selectQuery, id);
        return findMatching(query);
    }

    @Override
    public List<AttendanceDay> findByName(String date) {
        String query = String.format("%s WHERE date = %s;", selectQuery, date);
        return findMatching(query);
    }
}
