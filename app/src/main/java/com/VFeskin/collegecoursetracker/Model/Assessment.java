package com.VFeskin.collegecoursetracker.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * The following code defines a Assessment data entity.
 * Each instance of Assessment represents a row in a Assessment table in the app's database.
 */

@Entity(tableName = "Assessments", foreignKeys = @ForeignKey(entity = Course.class,
        parentColumns = "id",
        childColumns = "course_id",
        onDelete = ForeignKey.CASCADE))
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "assessment")
    @NonNull
    private String assessmentType;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "start_date_time")
    @NonNull
    private Date startDateTime;

    @ColumnInfo(name = "course_id")
    private int courseId; //FK

    // for creating new objects
    public Assessment(@NonNull String assessmentType, @NonNull String title, @NonNull Date startDateTime, int courseId) {
        this.assessmentType = assessmentType;
        this.title = title;
        this.startDateTime = startDateTime;
        this.courseId = courseId;
    }

    // for deleting and updating
    @Ignore
    public Assessment(int id, @NonNull String assessmentType, @NonNull String title, @NonNull Date startDateTime, int courseId) {
        this.id = id;
        this.assessmentType = assessmentType;
        this.title = title;
        this.startDateTime = startDateTime;
        this.courseId = courseId;
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
    public String getAssessmentType() {
        return assessmentType;
    }

    public int getCourseId() {
        return courseId;
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

    public void setAssessmentType(@NonNull String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
