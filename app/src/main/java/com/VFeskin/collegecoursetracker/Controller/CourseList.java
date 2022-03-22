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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.Objects;

/**
 * Controller class for course RecyclerView.
 * This class populates the RecyclerView with course cards,
 * which contain information on courses.
 */
public class CourseList extends AppCompatActivity implements CourseViewAdapter.OnCourseClickListener {

    // data
    private LiveData<List<Course>> courseList;

    // recycle view
    private RecyclerView recyclerView;
    private CourseViewAdapter courseViewAdapter;

    // course view model
    private CourseViewModel courseViewModel;

    // add button
//    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
//        fab = findViewById(R.id.add_new_course);

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
            courseViewAdapter = new CourseViewAdapter(courses, this);
            recyclerView.setAdapter(courseViewAdapter);
        });

//        // add new course
//        fab.setOnClickListener(view -> {
//            openNewCourse();
//        });
    }

//    public void openNewCourse() {
//        Intent intent = new Intent(this, NewCourse.class);
//        startActivity(intent);
//    }

    @Override
    public void onCourseClick(int position) {
        Intent intent = new Intent(this, DetailedCourse.class);
        // pass data to the detail view
        Course course = Objects.requireNonNull(courseViewModel.allCourses.getValue()).get(position);
        intent.putExtra("ID", course.getId());
        intent.putExtra("TITLE", course.getTitle());
        intent.putExtra("START", DateConverter.ToTimestamp(course.getStartDateTime()));
        intent.putExtra("END", DateConverter.ToTimestamp(course.getEndDateTime()));
        intent.putExtra("STATUS", course.getCourseStatus());
        intent.putExtra("NAME", course.getInstructorName());
        intent.putExtra("PHONE", course.getInstructorPhone());
        intent.putExtra("EMAIL", course.getInstructorEmail());

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
            case R.id.AllTerms:
                viewAllTerms();
                return true;
            case R.id.AllAssessments:
                viewAllAssessments();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void viewAllTerms() {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
        finish();
    }

    public void viewAllAssessments() {
        Intent intent = new Intent(this, AssessmentList.class);
        startActivity(intent);
        finish();
    }
}