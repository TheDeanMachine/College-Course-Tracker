package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;

import com.VFeskin.collegecoursetracker.Model.Note;
import com.VFeskin.collegecoursetracker.Model.NotesViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.google.android.material.snackbar.Snackbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

/**
 * This class is used for updating a note.
 * Displays a form that the user can make changes to and,
 * uses the information entered to update the note object/entity.
 */
public class EditNote extends AppCompatActivity {

    // XML attribute
    private EditText noteTxt;
    private Button updateNoteButton;

    // course PK
    private int PK;
    private int FK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        noteTxt = findViewById(R.id.editTextUpdateNoteMultiLine);
        updateNoteButton = findViewById(R.id.updateNoteButton);

        // get values and set text
        Bundle extra = getIntent().getExtras();
        PK =  extra.getInt("ID");
        noteTxt.setText(extra.getString("NOTE"));
        FK = extra.getInt("FK");


        updateNoteButton.setOnClickListener(view -> {
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

            NotesViewModel.update(new Note(PK, note, FK));
            finish();
        });


    }


}