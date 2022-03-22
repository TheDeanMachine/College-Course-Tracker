package com.VFeskin.collegecoursetracker.Model;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.VFeskin.collegecoursetracker.Database.CourseTrackerRepository;
import java.util.List;

/**
 * This class is the ViewModel for Notes.
 * The ViewModel's role is to provide data to the UI and survive configuration changes.
 * A ViewModel acts as a communication center between the Repository and the UI.
 */
public class NotesViewModel extends AndroidViewModel {

    public static CourseTrackerRepository repository;
    public final LiveData<List<Note>> allNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseTrackerRepository(application);
        allNotes = repository.getAllNotes();
    }

    // CRUD
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>> getNotesByCourseId(int id) {
        return repository.getNotesByCourseId(id);
    }

    public LiveData<Note> getByNotesPK(int id) {
        return repository.getByNotesPK(id);
    }

    public static void insert(Note note) {
        repository.insertNotes(note);
    }

    public static void update(Note note) {
        repository.updateNotes(note);
    }

    public static void delete(Note note) {
        repository.deleteNotes(note);
    }
}
