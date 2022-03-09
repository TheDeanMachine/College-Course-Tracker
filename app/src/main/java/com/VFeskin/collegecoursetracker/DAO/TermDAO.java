package com.VFeskin.collegecoursetracker.DAO;
import com.VFeskin.collegecoursetracker.Model.Term;

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
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM Terms ORDER BY start_date ASC")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM Terms WHERE id = :id")
    LiveData<Term> getById(int id);
}



