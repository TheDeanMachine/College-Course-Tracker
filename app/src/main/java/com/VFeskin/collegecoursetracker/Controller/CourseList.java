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
import java.util.List;

/**
 * Controller class for course RecyclerView.
 * This class populates the RecyclerView with course cards,
 * which contain information on courses.
 */
public class CourseList extends AppCompatActivity {

    // data
    private LiveData<List<Course>> courseList;

    // recycle view
    private RecyclerView recyclerView;
    private CourseViewAdapter courseViewAdapter;

    // course view model
    CourseViewModel courseViewModel;

    // add new course
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        fab = findViewById(R.id.add_new_course);

        // configure recycle view
        recyclerView = findViewById(R.id.recycler_course_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(CourseList.this
                .getApplication())
                .create(CourseViewModel.class);

        // observer
        courseViewModel.getAllCourses().observe(this, courses -> {
            // set recycle view with courses
            courseViewAdapter = new CourseViewAdapter(courses);
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


}