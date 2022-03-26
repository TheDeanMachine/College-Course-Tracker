package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Adapter.CourseViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.Model.TermViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.CourseCheckDialog;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * This class displays a selected term in detail.
 * The details include a RecyclerView with courses assigned to this term.
 */
public class DetailedTerm extends AppCompatActivity implements CourseViewAdapter.OnCourseClickListener {

    // XML attributes
    private TextView title;
    private TextView start;
    private TextView end;

    // data
    private int PK;
    private Date startDate;
    private Date endDate;
    private LiveData<List<Course>> courseList;
    private LiveData<List<Course>> coursesByTermId;
    private LiveData<Term> termById;

    // recycle view
    private RecyclerView recyclerView;
    private CourseViewAdapter courseViewAdapter;

    // view model
    private CourseViewModel courseViewModel;
    private TermViewModel termViewModel;

    // add button
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term);
        fab = findViewById(R.id.detail_add_new_course);
        title = findViewById(R.id.textViewDetailTitle);
        start = findViewById(R.id.textViewDetailStartDate);
        end = findViewById(R.id.textViewDetailEndDate);

        PK = getIntent().getIntExtra("ID", 0);

        // configure recycle view
        recyclerView = findViewById(R.id.detail_term_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedTerm.this
                .getApplication())
                .create(CourseViewModel.class);

        // creates an instance of view model to use
        termViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedTerm.this
                .getApplication())
                .create(TermViewModel.class);

        // observers
        coursesByTermId = courseViewModel.getByTermId(PK);
        coursesByTermId.observe(this, courses -> {
            // set recycle view with courses
            courseViewAdapter = new CourseViewAdapter(courses, DetailedTerm.this);
            recyclerView.setAdapter(courseViewAdapter);
        });

        termById = termViewModel.getByTermPK(PK);
        termById.observe(this, term -> {
            title.setText(term.getTitle());
            startDate = term.getStartDate();
            endDate = term.getEndDate();
            start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(term.getStartDate()));
            end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(term.getEndDate()));
        });

        // add new course
        fab.setOnClickListener(view -> {
            openNewCourse();
        });
    }

    public void openNewCourse() {
        Intent intent = new Intent(this, NewCourse.class);
        intent.putExtra("ID", PK);
        startActivity(intent);
    }

    @Override
    public void onCourseClick(int position) {
        Intent intent = new Intent(this, DetailedCourse.class);
        // pass data to the detail view
        Course course = Objects.requireNonNull(coursesByTermId.getValue()).get(position);
        intent.putExtra("ID", course.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which app bar item was chosen
        switch (item.getItemId()) {
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

    private void editItem() {
        Intent intent = new Intent(this, EditTerm.class);
        intent.putExtra("ID", PK);
        intent.putExtra("TITLE", title.getText());
        intent.putExtra("START", DateConverter.ToTimestamp(startDate));
        intent.putExtra("END", DateConverter.ToTimestamp(endDate));
        startActivity(intent);
    }

    private void deleteItem() {
        // check for any corresponding courses
        if (coursesByTermId.getValue().isEmpty()) {
            // no found courses, ok to delete
            if (termById.hasObservers()) {
                termById.removeObservers(DetailedTerm.this);
                TermViewModel.delete(new Term(PK, title.toString(), startDate, endDate));
                Toast.makeText(this, "Term deleted!", Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            // set alert for found courses
            DialogFragment newFragment = new CourseCheckDialog();
            newFragment.show(getSupportFragmentManager(), "found class");
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