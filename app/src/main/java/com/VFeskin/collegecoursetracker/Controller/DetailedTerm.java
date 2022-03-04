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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class DetailedTerm extends AppCompatActivity implements CourseViewAdapter.OnCourseClickListener {

    // XML attributes
    public TextView title;
    public TextView start;
    public TextView end;


    // data
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



        title = findViewById(R.id.textViewDetailTitle);

        title.setText(getIntent().getStringExtra("title"));




        fab = findViewById(R.id.detail_add_new_course);

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
        startActivity(intent);
    }


    @Override
    public void onCourseClick(int position) {

    }
}