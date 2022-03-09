package com.VFeskin.collegecoursetracker.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

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
    private Date startDate;

    @ColumnInfo(name = "end_date")
    @NonNull
    private Date endDate;

    // for creating new objects
    public Term(@NonNull String title, @NonNull Date startDate, @NonNull Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // for deleting and updating
    @Ignore
    public Term(int id, @NonNull String title, @NonNull Date startDate, @NonNull Date endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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

}
