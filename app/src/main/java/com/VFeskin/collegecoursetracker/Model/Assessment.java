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

    @ColumnInfo(name = "start_date")
    @NonNull
    private Date startDate;

    @ColumnInfo(name = "end_date")
    @NonNull
    private Date endDate;

    @ColumnInfo(name = "course_id")
    private int courseId; //FK

    // for creating new objects
    public Assessment(@NonNull String assessmentType, @NonNull String title, @NonNull Date startDate,
                      @NonNull Date endDate, int courseId) {
        this.assessmentType = assessmentType;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseId = courseId;
    }

    // for deleting and updating
    @Ignore
    public Assessment(int id, @NonNull String assessmentType, @NonNull String title,
                      @NonNull Date startDate, @NonNull Date endDate, int courseId) {
        this.id = id;
        this.assessmentType = assessmentType;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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
    public Date getStartDate() {
        return startDate;
    }

    @NonNull
    public Date getEndDate() {
        return endDate;
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

    public void setStartDate(@NonNull Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(@NonNull Date endDate) {
        this.endDate = endDate;
    }

    public void setAssessmentType(@NonNull String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
