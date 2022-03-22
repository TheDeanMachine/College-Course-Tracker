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

    @ColumnInfo(name = "start_date_time")
    @NonNull
    private Date startDateTime;

    @ColumnInfo(name = "end_date_time")
    @NonNull
    private Date endDateTime;

    @ColumnInfo(name = "instructor_name")
    private String instructorName;

    @ColumnInfo(name = "instructor_phone")
    private String instructorPhone;

    @ColumnInfo(name = "instructor_email")
    private String instructorEmail;

    @ColumnInfo(name = "course_status")
    private String courseStatus;

    @ColumnInfo(name = "room_number")
    private String roomNumber;

    @ColumnInfo(name = "term_id")
    private int termId; //FK


    // for creating new objects
    public Course(@NonNull String title, @NonNull Date startDateTime, @NonNull Date endDateTime,
                  String instructorName, String instructorPhone, String instructorEmail,
                  String courseStatus, String roomNumber, int termId) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.courseStatus = courseStatus;
        this.roomNumber = roomNumber;
        this.termId = termId;
    }

    // for deleting and updating
    @Ignore
    public Course(int id, @NonNull String title, @NonNull Date startDateTime, @NonNull Date endDateTime,
                  String instructorName, String instructorPhone,
                  String instructorEmail, String courseStatus, String roomNumber, int termId) {
        this.id = id;
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.courseStatus = courseStatus;
        this.roomNumber = roomNumber;
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
    public Date getStartDateTime() {
        return startDateTime;
    }

    @NonNull
    public Date getEndDateTime() {
        return endDateTime;
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

    public String getRoomNumber() {
        return roomNumber;
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

    public void setStartDateTime(@NonNull Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(@NonNull Date endDateTime) {
        this.endDateTime = endDateTime;
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

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

}
