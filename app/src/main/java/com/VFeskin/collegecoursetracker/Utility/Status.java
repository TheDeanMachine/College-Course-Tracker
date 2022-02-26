package com.VFeskin.collegecoursetracker.Utility;

public enum Status {
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    DROPPED("Dropped"),
    PLAN_TO_TAKE("Plan to take");

    private String displayString;

    Status(String displayString) {
        this.displayString = displayString;
    }

    @Override
    public String toString() {
        return displayString;
    }
}
