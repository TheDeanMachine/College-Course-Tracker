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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


public class DetailedCourse extends AppCompatActivity implements AssessmentViewAdapter.OnAssessmentClickListener {

    // XML attributes
    public TextView title;
    public TextView start;
    public TextView end;
    public TextView status;
    public TextView name;
    public TextView phone;
    public TextView email;

    // data
    private int id;
    private Date startDate;
    private Date endDate;
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
        setContentView(R.layout.activity_detailed_course);
        fab = findViewById(R.id.detail_add_new_assessment);
        title = findViewById(R.id.textViewDetailCourseTitle);
        start = findViewById(R.id.textViewDetailCourseStartDate);
        end = findViewById(R.id.textViewDetailCourseEndDate);
        status = findViewById(R.id.textViewDetailCourseStatus);
        name = findViewById(R.id.textViewDetailCourseInstructorName);
        phone = findViewById(R.id.textViewDetailCourseInstructorPhone);
        email = findViewById(R.id.textViewDetailCourseInstructorEmail);

        // get values from term card and set text
        Bundle extra = getIntent().getExtras();
        id = extra.getInt("ID");
        title.setText(extra.getString("TITLE"));
        startDate = DateConverter.fromTimestamp(extra.getLong("START"));
        start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        endDate = DateConverter.fromTimestamp(extra.getLong("END"));
        end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));
        status.setText(extra.getString("STATUS"));
        name.setText(extra.getString("NAME"));
        phone.setText(extra.getString("PHONE"));
        email.setText(extra.getString("EMAIL"));

        // configure recycle view
        recyclerView = findViewById(R.id.detail_course_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedCourse.this
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


    }
}