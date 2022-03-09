package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Adapter.TermViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.Model.TermViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.List;
import java.util.Objects;

/**
 * Controller class for term RecyclerView.
 * This class populates the RecyclerView with term cards,
 * which contain information on terms.
 */
public class TermList extends AppCompatActivity implements TermViewAdapter.OnTermClickListener {

    // data
    private LiveData<List<Term>> termList;

    // recycle view
    private RecyclerView recyclerView;
    private TermViewAdapter termViewAdapter;

    // term view model
    private TermViewModel termViewModel;

    // add button
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        fab = findViewById(R.id.add_new_term);

        // configure recycle view
        recyclerView = findViewById(R.id.recycler_view_term);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        termViewModel = new ViewModelProvider.AndroidViewModelFactory(TermList.this
                .getApplication())
                .create(TermViewModel.class);

        // observer
        termViewModel.getAllTerms().observe(this, terms -> {
            // set recycle view with terms
            termViewAdapter = new TermViewAdapter(terms, this);
            recyclerView.setAdapter(termViewAdapter);
        });

        // add new term
        fab.setOnClickListener(view -> {
            openNewTerm();
        });
    }

    public void openNewTerm() {
        Intent intent = new Intent(this, NewTerm.class);
        startActivity(intent);
    }

    @Override
    public void onTermClick(int position) {
        Intent intent = new Intent(this, DetailedTerm.class);
        // pass data to the detail view
        Term term = Objects.requireNonNull(termViewModel.allTerms.getValue()).get(position);
        intent.putExtra("ID", term.getId());
//        intent.putExtra("TITLE", term.getTitle());
//        intent.putExtra("START", DateConverter.ToTimestamp(term.getStartDate()));
//        intent.putExtra("END", DateConverter.ToTimestamp(term.getEndDate()));

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which app bar item was chosen
        switch (item.getItemId()) {
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

    public void viewAllCourses() {
        Intent intent = new Intent(this, CourseList.class);
        startActivity(intent);
    }

    public void viewAllAssessments() {
        Intent intent = new Intent(this, AssessmentList.class);
        startActivity(intent);
    }

}