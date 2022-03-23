package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import com.VFeskin.collegecoursetracker.Model.Note;
import com.VFeskin.collegecoursetracker.Model.NotesViewModel;
import com.VFeskin.collegecoursetracker.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.Objects;

/**
 * This class displays a selected note in detail.
 */
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem shareItem = menu.findItem(R.id.share);
        shareItem.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which app bar item was chosen
        switch (item.getItemId()) {
            case R.id.share:
                shareItem();
                return true;
            case R.id.edit:
                editItem();
                return true;
            case R.id.delete:
                deleteItem();
                return true;
            case R.id.AllTerms:
                viewAllTerms();
                return true;
            case R.id.AllCourses:
                viewAllCourses();
                return true;
            case R.id.AllAssessments:
                viewAllAssessments();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareItem() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, Objects.requireNonNull(noteById.getValue()).getNote());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void editItem() {
        Intent intent = new Intent(this, EditNote.class);
        intent.putExtra("ID", PK);
        intent.putExtra("NOTE", notes.getText());
        intent.putExtra("FK", FK);
        startActivity(intent);
    }

    private void deleteItem() {
        if (noteById.hasObservers()) {
            noteById.removeObservers(this);
            NotesViewModel.delete(new Note(PK, notes.toString(), FK));
            finish();
        }
    }

    public void viewAllTerms() {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
        finish();
    }

    public void viewAllCourses() {
        Intent intent = new Intent(this, CourseList.class);
        startActivity(intent);
        finish();
    }

    public void viewAllAssessments() {
        Intent intent = new Intent(this, AssessmentList.class);
        startActivity(intent);
        finish();
    }


}