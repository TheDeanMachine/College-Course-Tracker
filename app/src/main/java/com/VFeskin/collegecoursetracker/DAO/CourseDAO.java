package com.VFeskin.collegecoursetracker.DAO;

import com.VFeskin.collegecoursetracker.Model.Course;
import androidx.lifecycle.LiveData;
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
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM Courses ORDER BY start_date_time ASC")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM Courses WHERE term_id = :id ORDER BY start_date_time ASC")
    LiveData<List<Course>> getByTermId(int id);

    @Query("SELECT * FROM Courses WHERE id = :id")
    LiveData<Course> getByCoursePK(int id);

    @Query("SELECT * FROM Courses WHERE title LIKE :query")
    LiveData<List<Course>> searchForCourses(String query);

    @Query("SELECT * FROM Courses WHERE course_status = :status")
    LiveData<List<Course>> resultsByCourseStatus(String status);
}
