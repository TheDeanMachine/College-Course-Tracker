package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.VFeskin.collegecoursetracker.Model.Note;
import com.VFeskin.collegecoursetracker.Model.NotesViewModel;
import com.VFeskin.collegecoursetracker.R;
import android.os.Bundle;
import android.widget.TextView;

public class DetailedNote extends AppCompatActivity {

    // XML attributes
    private TextView notes;

    // data
    private int PK;
    private int FK;
    private LiveData<Note> noteById;

    // view model;
    private NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);
        notes = findViewById(R.id.textViewDetailNote);

        PK = getIntent().getIntExtra("ID", 0);

        // creates an instance of view model to use
        notesViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedNote.this
                .getApplication())
                .create(NotesViewModel.class);

        noteById = notesViewModel.getByNotesPK(PK);
        noteById.observe(this, note -> {
            notes.setText(note.getNote());
            FK = note.getCourseId();
        });
    }
}