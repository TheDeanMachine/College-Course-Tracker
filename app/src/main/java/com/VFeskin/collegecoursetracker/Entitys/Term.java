package com.VFeskin.collegecoursetracker.Entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The following code defines a Term data entity.
 * Each instance of Term represents a row in a Term table in the app's database.
 */
@Entity(tableName = "Terms")
public class Term {

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

    public Term(@NonNull String title, @NonNull LocalDate startDate, @NonNull LocalDate endDate) {
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
    public LocalDate getStartDate() {
        return startDate;
    }

    @NonNull
    public LocalDate getEndDate() {
        return endDate;
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


}
