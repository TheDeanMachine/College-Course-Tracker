package com.VFeskin.collegecoursetracker.DAO;

import com.VFeskin.collegecoursetracker.Model.Note;
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
public interface NotesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM Notes ORDER BY id ASC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM Notes WHERE id = :id ORDER BY id ASC")
    LiveData<List<Note>> getNotesByCourseId(int id);

    @Query("SELECT * FROM Notes WHERE id = :id")
    LiveData<Note> getByNotesPK(int id);
}
