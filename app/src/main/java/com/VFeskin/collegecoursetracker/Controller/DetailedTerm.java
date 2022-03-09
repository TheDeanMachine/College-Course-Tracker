package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Adapter.CourseViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
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
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DetailedTerm extends AppCompatActivity implements CourseViewAdapter.OnCourseClickListener {

    // XML attributes
    private TextView title;
    private TextView start;
    private TextView end;

    // data
    private int id; // PK
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

        // get values from term card and set text
        Bundle extra = getIntent().getExtras();
        id = extra.getInt("ID");
//        title.setText(extra.getString("TITLE"));
//        startDate = DateConverter.fromTimestamp(extra.getLong("START"));
//        start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
//        endDate = DateConverter.fromTimestamp(extra.getLong("END"));
//        end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));

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

        // observer
        coursesByTermId = courseViewModel.getByTermId(id);
        coursesByTermId.observe(this, courses -> {
            // set recycle view with courses
            courseViewAdapter = new CourseViewAdapter(courses, DetailedTerm.this);
            recyclerView.setAdapter(courseViewAdapter);
        });

        termById = termViewModel.getById(id);
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
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    @Override
    public void onCourseClick(int position) {
        Intent intent = new Intent(this, DetailedCourse.class);

        // pass data to the detail view
        Course course = Objects.requireNonNull(coursesByTermId.getValue()).get(position);
        intent.putExtra("ID", course.getId());
        intent.putExtra("TITLE", course.getTitle());
        intent.putExtra("START", DateConverter.ToTimestamp(course.getStartDate()));
        intent.putExtra("END", DateConverter.ToTimestamp(course.getEndDate()));
        intent.putExtra("STATUS", course.getCourseStatus());
        intent.putExtra("NAME", course.getInstructorName());
        intent.putExtra("PHONE", course.getInstructorPhone());
        intent.putExtra("EMAIL", course.getInstructorEmail());
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
        intent.putExtra("ID", id);
        intent.putExtra("TITLE", title.getText());
        intent.putExtra("START", DateConverter.ToTimestamp(startDate));
        intent.putExtra("END", DateConverter.ToTimestamp(endDate));
        startActivity(intent);
    }

    private void deleteItem() {

    }

    public void viewAllTerms() {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
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