package com.VFeskin.collegecoursetracker.DAO;
import com.VFeskin.collegecoursetracker.Entitys.Assessment;

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
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM Assessments ORDER BY start_date ASC")
    LiveData<List<Assessment>> getAllAssessments();
}
