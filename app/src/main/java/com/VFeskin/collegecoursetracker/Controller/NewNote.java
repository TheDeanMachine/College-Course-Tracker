package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;

import com.VFeskin.collegecoursetracker.Model.Note;
import com.VFeskin.collegecoursetracker.Model.NotesViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.google.android.material.snackbar.Snackbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class NewNote extends AppCompatActivity {

    // XML attribute
    private EditText noteTxt;
    private Button createNoteButton;

    // course PK
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        noteTxt = findViewById(R.id.editTextNoteMultiLine);
        createNoteButton = findViewById(R.id.createNoteButton);

        id = getIntent().getIntExtra("ID", 0);

        // create new note
        createNoteButton.setOnClickListener(view -> {
            // input validation
            String note = null;
            try {
                note = noteTxt.getText().toString();
                if(note == null || note.isEmpty()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                noteTxt.setError("Note is required!");
                Snackbar.make(view, "Please enter a note", Snackbar.LENGTH_SHORT).show();
                return;
            }

            id = getIntent().getIntExtra("ID", 0); // FK
            NotesViewModel.insert(new Note(note, id));
            finish();
        });
    }




}