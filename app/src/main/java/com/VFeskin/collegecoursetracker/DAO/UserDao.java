package com.VFeskin.collegecoursetracker.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.VFeskin.collegecoursetracker.Model.User;
import java.util.List;

/**
 * This class is used to establish CRUD operations.
 * At compile time, Room automatically generates implementations of the DAO.
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * FROM Users ORDER BY id ASC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM Users WHERE user_name = :user AND password =:password")
    LiveData<User> getUser(String user, String password);

    @Query("SELECT * FROM Users WHERE user_name = :user")
    LiveData<User> getUserName(String user);

    @Query("SELECT * FROM Users WHERE user_name = :user")
    User getUserNameTest(String user);

    @Query("SELECT * FROM Users WHERE password = :password")
    LiveData<User> getUserPassword(String password);
}
