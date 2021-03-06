package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Adapter.AssessmentViewAdapter;
import com.VFeskin.collegecoursetracker.Adapter.CourseViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
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
 * Controller class for Assessment RecyclerView.
 * This class populates the RecyclerView with assessment cards,
 * which contain information on assessments.
 */
public class AssessmentList extends AppCompatActivity implements AssessmentViewAdapter.OnAssessmentClickListener {

    // recycle view
    private RecyclerView recyclerView;
    private AssessmentViewAdapter assessmentViewAdapter;

    // assessment view model
    private AssessmentViewModel assessmentViewModel;

    // dropdown
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> arrayAdapter;
    private TextInputLayout dropdownLayout;

    // check dropdown selection
    private String testType = null;

    // holds results list
    private LiveData<List<Assessment>> viewAllByType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        autoCompleteTextView = findViewById(R.id.reportsDropdownText);
        dropdownLayout = findViewById(R.id.reportsDropdownLayout);

        // set exposed dropdown with items
        arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, getResources().getStringArray(R.array.test_type));
        autoCompleteTextView.setAdapter(arrayAdapter);

        // collect the item selected, onItemClick
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            testType = (String) parent.getAdapter().getItem(position);
            results(testType);
        });

        // configure recycle view
        recyclerView = findViewById(R.id.recycler_Assessment_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(AssessmentList.this
                .getApplication())
                .create(AssessmentViewModel.class);

        // observer
        assessmentViewModel.getAllAssessment().observe(this, assessments -> {
            // set recycle view with assessments
            assessmentViewAdapter = new AssessmentViewAdapter(assessments, this);
            recyclerView.setAdapter(assessmentViewAdapter);
        });
    }

    // sets recycler view with results of dropdown selection
    public void results(String type) {
        viewAllByType = assessmentViewModel.resultsByType(type);
        viewAllByType.observe(this, assessments -> {
            assessmentViewAdapter = new AssessmentViewAdapter(assessments, this);
            recyclerView.setAdapter(assessmentViewAdapter);
        });
    }

    @Override
    public void onAssessmentClick(int position) {
        Intent intent = new Intent(this, DetailedAssessment.class);
        // pass data to the detail view
        Assessment assessment = Objects.requireNonNull(assessmentViewModel.allAssessments.getValue()).get(position);
        intent.putExtra("ID", assessment.getId());
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
        assessmentViewModel.searchForAssessments(searchQuery).observe( this, result -> {
            assessmentViewAdapter = new AssessmentViewAdapter(result, this);
            recyclerView.setAdapter(assessmentViewAdapter);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        autoCompleteTextView.setText(""); // clear dropdown prior to search

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