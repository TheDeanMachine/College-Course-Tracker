package com.VFeskin.collegecoursetracker.Utility;

// Deprecated class, was used for course status
public enum Status {
//    HINT_TEXT("Select course status"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    DROPPED("Dropped"),
    PLAN_TO_TAKE("Plan To Take");

    private String displayString;

    Status(String displayString) {
        this.displayString = displayString;
    }

    @Override
    public String toString() {
        return displayString;
    }
}
