package com.VFeskin.collegecoursetracker.Entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.time.LocalDate;


/**
 * The following code defines a Assessment data entity.
 * Each instance of Assessment represents a row in a Assessment table in the app's database.
 */
public class PerformanceAssessment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "start_date")
    @NonNull
    private LocalDate startDate;

    @ColumnInfo(name = "end_date")
    @NonNull
    private LocalDate endDate;

    public PerformanceAssessment(@NonNull String title, @NonNull LocalDate startDate, @NonNull LocalDate endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // setters
    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setStartDate(@NonNull LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(@NonNull LocalDate endDate) {
        this.endDate = endDate;
    }

    // getters
    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public LocalDate getStartDate() {
        return startDate;
    }

    @NonNull
    public LocalDate getEndDate() {
        return endDate;
    }
}
