package com.VFeskin.collegecoursetracker.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * The following code defines a Note data entity.
 * Each instance of Note represents a row in a Notes table in the app's database.
 */
@Entity(tableName = "Notes", foreignKeys = @ForeignKey(entity = Course.class,
        parentColumns = "id",
        childColumns = "course_id",
        onDelete = ForeignKey.CASCADE))
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "course_id")
    private int courseId;

    // for creating new objects
    public Note(String note, int courseId) {
        this.note = note;
        this.courseId = courseId;
    }

    // for deleting and updating
    @Ignore
    public Note(int id, String note, int courseId) {
        this.id = id;
        this.note = note;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
