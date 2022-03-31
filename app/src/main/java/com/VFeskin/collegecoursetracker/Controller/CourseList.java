package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Adapter.CourseViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.google.android.material.textfield.TextInputLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import java.util.List;
import java.util.Objects;

/**
 * Controller class for course RecyclerView.
 * This class populates the RecyclerView with course cards,
 * which contain information on courses.
 */
public class CourseList extends AppCompatActivity implements CourseViewAdapter.OnCourseClickListener {

    // recycle view
    private RecyclerView recyclerView;
    private CourseViewAdapter courseViewAdapter;

    // course view model
    private CourseViewModel courseViewModel;

    // dropdown
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> arrayAdapter;
    private TextInputLayout dropdownLayout;

    // check dropdown selection
    private String courseType = null;

    // holds results list
    private LiveData<List<Course>> viewAllByType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        autoCompleteTextView = findViewById(R.id.courseReportsDropdownText);
        dropdownLayout = findViewById(R.id.courseReportsDropdownLayout);

        // set exposed dropdown with items
        arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, getResources().getStringArray(R.array.course_status));
        autoCompleteTextView.setAdapter(arrayAdapter);

        // collect the item selected, onItemClick
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            courseType = (String) parent.getAdapter().getItem(position);
            results(courseType);
        });

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
    }

    private void results(String status) {
        viewAllByType = courseViewModel.resultsByCourseStatus(status);
        viewAllByType.observe(this, courses -> {
            // set recycle view with courses
            courseViewAdapter = new CourseViewAdapter(courses, this);
            recyclerView.setAdapter(courseViewAdapter);
        });

    }

    @Override
    public void onCourseClick(int position) {
        Intent intent = new Intent(this, DetailedCourse.class);
        // pass data to the detail view
        Course course = Objects.requireNonNull(courseViewModel.allCourses.getValue()).get(position);
        intent.putExtra("ID", course.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    searchDataBase(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    searchDataBase(newText);
                }
                return true;
            }
        });
        return true;
    }

    private void searchDataBase(String query) {
        String searchQuery = "%" + query + "%";
        courseViewModel.searchForCourses(searchQuery).observe( this, result -> {
            courseViewAdapter = new CourseViewAdapter(result, this);
            recyclerView.setAdapter(courseViewAdapter);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        autoCompleteTextView.setText(""); // clear your TextView prior to search

        // Determine which app bar item was chosen
        switch (item.getItemId()) {
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