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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;
import android.os.Bundle;
import java.util.List;

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

    // add button
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        fab = findViewById(R.id.add_new_Assessment);

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

        // add new assessment
        fab.setOnClickListener(view -> {
            openNewAssessment();
        });
    }

    private void openNewAssessment() {
        Intent intent = new Intent(this, NewAssessment.class);
        startActivity(intent);
    }

    @Override
    public void onAssessmentClick(int position) {
//        Intent intent = new Intent(this, DetailedAssessment);
//        startActivity(intent);
    }
}