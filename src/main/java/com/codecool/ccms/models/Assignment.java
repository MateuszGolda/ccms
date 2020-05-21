package com.codecool.ccms.models;

public class Assignment {
    private String title;
    private String description;
    private boolean isAvailable;

    public Assignment(String title, String description, boolean isAvailable){
        this.title = title;
        this.description = description;
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
