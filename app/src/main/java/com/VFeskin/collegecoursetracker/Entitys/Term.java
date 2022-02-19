package com.VFeskin.collegecoursetracker.Entitys;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    private String startDate;

    @ColumnInfo(name = "end_date")
    @NonNull
    private String endDate;

    public Term(@NonNull String title, @NonNull String startDate, @NonNull String endDate) {
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


}
