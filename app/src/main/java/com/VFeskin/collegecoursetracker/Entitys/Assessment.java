package com.VFeskin.collegecoursetracker.Entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The following code defines a Assessment data entity.
 * Each instance of Assessment represents a row in a Assessment table in the app's database.
 */

@Entity(tableName = "Assessments")
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
    private String startDate;

    @ColumnInfo(name = "end_date")
    @NonNull
    private String endDate;

    public Assessment(@NonNull String assessmentType, @NonNull String title, @NonNull String startDate, @NonNull String endDate) {
        this.assessmentType = assessmentType;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // getters
    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getStartDate() {
        return startDate;
    }

    @NonNull
    public String getEndDate() {
        return endDate;
    }

    @NonNull
    public String getAssessmentType() {
        return assessmentType;
    }

    // setters
    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setStartDate(@NonNull String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(@NonNull String endDate) {
        this.endDate = endDate;
    }

    public void setAssessmentType(@NonNull String assessmentType) {
        this.assessmentType = assessmentType;
    }
}
