package com.codecool.ccms.models;

import java.time.LocalDateTime;
import java.util.Map;

public class AttendanceDay {
    Map<User, Presence> studentsPresence;
    LocalDateTime attendanceDay;

    public AttendanceDay(Map<User, Presence> studentsPresence, LocalDateTime attendanceDay) {
        this.studentsPresence = studentsPresence;
        this.attendanceDay = attendanceDay;
    }
}
