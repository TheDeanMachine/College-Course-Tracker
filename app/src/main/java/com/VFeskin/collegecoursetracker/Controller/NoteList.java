package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Adapter.NoteViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Note;
import com.VFeskin.collegecoursetracker.Model.NotesViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;
import android.os.Bundle;
import java.util.List;
import java.util.Objects;

/**
 * Controller class for note RecyclerView.
 * This class populates the RecyclerView with note cards,
 * which contain information on notes.
 */
public class NoteList extends AppCompatActivity implements NoteViewAdapter.OnNoteClickListener {

    // recycle view
    private RecyclerView recyclerView;
    private NoteViewAdapter noteViewAdapter;

    // view model;
    private NotesViewModel notesViewModel;

    // add button
    private FloatingActionButton fab;

    // course PK
    private int PK;

    // data
    private LiveData<List<Note>> noteById;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        fab = findViewById(R.id.add_new_note);

        PK = getIntent().getIntExtra("ID", 0);

        // configure recycle view
        recyclerView = findViewById(R.id.recycler_view_note);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        notesViewModel = new ViewModelProvider.AndroidViewModelFactory(NoteList.this
                .getApplication())
                .create(NotesViewModel.class);

        // observer all
//        notesViewModel.getAllNotes().observe(this, notes -> {
//            // recycle view with notes
//            noteViewAdapter = new NoteViewAdapter(notes, this);
//            recyclerView.setAdapter(noteViewAdapter);
//        });

        // observer by course
        noteById = notesViewModel.getNotesByCourseId(PK);
        noteById.observe(this, notes -> {
            noteViewAdapter = new NoteViewAdapter(notes, this);
            recyclerView.setAdapter(noteViewAdapter);
        });

        // add new note
        fab.setOnClickListener(view -> {
            openNewNote();
        });
    }

    private void openNewNote() {
        Intent intent = new Intent(this, NewNote.class);
        intent.putExtra("ID", PK);
        startActivity(intent);
    }


    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, DetailedNote.class);
        // pass data to the detail view
        Note note = Objects.requireNonNull(notesViewModel.allNotes.getValue()).get(position);
        intent.putExtra("ID", note.getId());
        startActivity(intent);

    }
}