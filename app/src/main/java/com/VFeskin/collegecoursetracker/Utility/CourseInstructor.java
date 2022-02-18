package com.VFeskin.collegecoursetracker.Utility;

/**
 * The class creates a course instructor.
 * The class is used in Course Entity's to represent an instructor.
 */
public class CourseInstructor {

    // The course instructor name, phone number, and e-mail address
    private String name;
    private String phoneNumber;
    private String email;

    public CourseInstructor(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
