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
import com.google.android.material.textfield.TextInputLayout;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

public class Reports extends AppCompatActivity implements AssessmentViewAdapter.OnAssessmentClickListener {

    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> arrayAdapter;
    private TextInputLayout dropdownLayout;

    // recycle view
    private RecyclerView recyclerView;
    private AssessmentViewAdapter assessmentViewAdapter;

    // assessment view model
    private AssessmentViewModel assessmentViewModel;

    // check dropdown selection
    private boolean isSelect = false;
    private String testType = null;

    private LiveData<List<Assessment>> assessmentByQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        autoCompleteTextView = findViewById(R.id.reportsDropdownText);
        dropdownLayout = findViewById(R.id.reportsDropdownLayout);

        // set exposed dropdown with items
        arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, getResources().getStringArray(R.array.test_type));
        autoCompleteTextView.setAdapter(arrayAdapter);

        // collect the item selected, onItemClick
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            testType = (String) parent.getAdapter().getItem(position);
            isSelect = true;
        });

        // configure recycle view
        recyclerView = findViewById(R.id.recycler_Assessment_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(Reports.this
                .getApplication())
                .create(AssessmentViewModel.class);

        // observer
        assessmentViewModel.getAllAssessment().observe(this, assessments -> {
            // set recycle view with assessments
            assessmentViewAdapter = new AssessmentViewAdapter(assessments, this);
            recyclerView.setAdapter(assessmentViewAdapter);
        });


    }

    @Override
    public void onAssessmentClick(int position) {
        // do nothing
    }
}