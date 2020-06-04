package com.codecool.ccms.models;

public enum Presence {
    PRESENT(1), ABSENT(2), LATE(3), EXCUSED(4);

    private final int value;

    Presence(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
