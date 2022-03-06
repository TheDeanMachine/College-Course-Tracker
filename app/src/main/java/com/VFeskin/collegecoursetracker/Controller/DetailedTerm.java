package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.VFeskin.collegecoursetracker.Adapter.CourseViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private int id;
    private Date startDate;
    private Date endDate;
    private LiveData<List<Course>> courseList;

    // recycle view
    private RecyclerView recyclerView;
    private CourseViewAdapter courseViewAdapter;

    // course view model
    private CourseViewModel courseViewModel;

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
        title.setText(extra.getString("TITLE"));
        startDate = DateConverter.fromTimestamp(extra.getLong("START"));
        start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        endDate = DateConverter.fromTimestamp(extra.getLong("END"));
        end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));


        // get values from term card and set text
//        id = getIntent().getIntExtra("ID", 0);
//        title.setText(getIntent().getStringExtra("TITLE"));
//        startDate = DateConverter.fromTimestamp(getIntent().getLongExtra("START", 0));
//        start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
//        endDate = DateConverter.fromTimestamp(getIntent().getLongExtra("END", 0));
//        end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));

        // configure recycle view
        recyclerView = findViewById(R.id.detail_term_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedTerm.this
                .getApplication())
                .create(CourseViewModel.class);

        //TODO : change to get courses based on term id

        // observer
        courseViewModel.getAllCourses().observe(this, courses -> {
            // set recycle view with courses
            courseViewAdapter = new CourseViewAdapter(courses, this);
            recyclerView.setAdapter(courseViewAdapter);
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
        Course course = Objects.requireNonNull(courseViewModel.allCourses.getValue()).get(position);
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
}