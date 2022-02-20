package com.VFeskin.collegecoursetracker.DAO;
import com.VFeskin.collegecoursetracker.Entitys.Course;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
 * This class is used to establish CRUD operations.
 * At compile time, Room automatically generates implementations of the DAO.
 */
@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course term);

    @Update
    void update(Course term);

    @Delete
    void delete(Course term);

    @Query("SELECT * FROM Courses")
    List<Course> getAllCourses();
}
