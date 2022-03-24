package com.VFeskin.collegecoursetracker.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The following code defines a Term User entity.
 * Each instance of user represents a row in a Users table in the app's database.
 */

@Entity(tableName = "Users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_name")
    @NonNull
    private String userName;

    @NonNull
    private String password;

    public User(@NonNull String userName, @NonNull String password) {
        this.userName = userName;
        this.password = password;
    }

    // getters
    public int getId() {
        return id;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    // setters

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
