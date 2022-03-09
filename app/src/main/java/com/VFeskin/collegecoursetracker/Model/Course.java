package com.VFeskin.collegecoursetracker.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;


/**
 * The following code defines a Course data entity.
 * Each instance of Course represents a row in a Course table in the app's database.
 */
@Entity(tableName = "Courses", foreignKeys = @ForeignKey(entity = Term.class,
        parentColumns = "id",
        childColumns = "term_id",
        onDelete = ForeignKey.CASCADE))
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "start_date")
    @NonNull
    private Date startDate;

    @ColumnInfo(name = "end_date")
    @NonNull
    private Date endDate;

    @ColumnInfo(name = "instructor_name")
    private String instructorName;

    @ColumnInfo(name = "instructor_phone")
    private String instructorPhone;

    @ColumnInfo(name = "instructor_email")
    private String instructorEmail;

    @ColumnInfo(name = "course_status")
    private String courseStatus;

    @ColumnInfo(name = "term_id")
    private int termId; //FK

    // for creating new objects
    public Course(@NonNull String title, @NonNull Date startDate, @NonNull Date endDate,
                  String instructorName, String instructorPhone, String instructorEmail,
                  String courseStatus, int termId) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.courseStatus = courseStatus;
        this.termId = termId;
    }

    // for deleting and updating
    @Ignore
    public Course(int id, @NonNull String title, @NonNull Date startDate, @NonNull Date endDate,
                  String instructorName, String instructorPhone,
                  String instructorEmail, String courseStatus, int termId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.courseStatus = courseStatus;
        this.termId = termId;
    }

    // getters
    public int getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public Date getStartDate() {
        return startDate;
    }

    @NonNull
    public Date getEndDate() {
        return endDate;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public int getTermId() {
        return termId;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setStartDate(@NonNull Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(@NonNull Date endDate) {
        this.endDate = endDate;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
