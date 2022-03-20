package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Adapter.AssessmentViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.Objects;

/**
 * Controller class for Assessment RecyclerView.
 * This class populates the RecyclerView with assessment cards,
 * which contain information on assessments.
 */
public class AssessmentList extends AppCompatActivity implements AssessmentViewAdapter.OnAssessmentClickListener {

    // data
    private LiveData<List<Assessment>> assessmentList;

    // recycle view
    private RecyclerView recyclerView;
    private AssessmentViewAdapter assessmentViewAdapter;

    // assessment view model
    private AssessmentViewModel assessmentViewModel;

//    // add button
//    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
//        fab = findViewById(R.id.add_new_Assessment);

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
//
//        // add new assessment
//        fab.setOnClickListener(view -> {
//            openNewAssessment();
//        });
    }

//    private void openNewAssessment() {
//        Intent intent = new Intent(this, NewAssessment.class);
//        startActivity(intent);
//    }

    @Override
    public void onAssessmentClick(int position) {
        Intent intent = new Intent(this, DetailedAssessment.class);
        // pass data to the detail view
        Assessment assessment = Objects.requireNonNull(assessmentViewModel.allAssessments.getValue()).get(position);
        intent.putExtra("ID", assessment.getId());
        intent.putExtra("TITLE", assessment.getTitle());
        intent.putExtra("TEST", assessment.getAssessmentType());
        intent.putExtra("START", DateConverter.ToTimestamp(assessment.getStartDateTime()));
//        intent.putExtra("END", DateConverter.ToTimestamp(assessment.getEndDate()));
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
            case R.id.AllCourses:
                viewAllCourses();
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

}