package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Adapter.AssessmentViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * This class displays a selected course in detail.
 * The details include a RecyclerView with assessments assigned to this course.
 */
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
    private int PK;
    private int FK;
    private Date startDate;
    private Date endDate;
    private LiveData<List<Assessment>> assessmentList;
    private LiveData<List<Assessment>> assessmentsByCourseId;
    private LiveData<Course> courseById;

    // recycle view
    private RecyclerView recyclerView;
    private AssessmentViewAdapter assessmentViewAdapter;

    // view model
    private CourseViewModel courseViewModel;
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

        PK = getIntent().getIntExtra("ID", 0);

        // configure recycle view
        recyclerView = findViewById(R.id.detail_course_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedCourse.this
                .getApplication())
                .create(AssessmentViewModel.class);

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedCourse.this
                .getApplication())
                .create(CourseViewModel.class);

        // observers
        assessmentsByCourseId = assessmentViewModel.getByCourseId(PK);
        assessmentsByCourseId.observe(this, assessments -> {
            // set recycle view with assessments
            assessmentViewAdapter = new AssessmentViewAdapter(assessments, this);
            recyclerView.setAdapter(assessmentViewAdapter);
        });

        courseById = courseViewModel.getByCoursePK(PK);
        courseById.observe(this, course -> {
            title.setText(course.getTitle());
            startDate = course.getStartDate();
            endDate = course.getEndDate();
            start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(course.getStartDate()));
            end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(course.getEndDate()));
            status.setText(course.getCourseStatus());
            name.setText(course.getInstructorName());
            phone.setText(course.getInstructorPhone());
            email.setText(course.getInstructorEmail());
            FK = course.getTermId();
        });

        // add new assessment
        fab.setOnClickListener(view -> {
            openNewAssessment();
        });
    }

    private void openNewAssessment() {
        Intent intent = new Intent(this, NewAssessment.class);
        intent.putExtra("ID", PK);
        startActivity(intent);
    }

    @Override
    public void onAssessmentClick(int position) {
        Intent intent = new Intent(this, DetailedAssessment.class);

        // pass data to the detail view
        Assessment assessment = Objects.requireNonNull(assessmentsByCourseId.getValue()).get(position);
        intent.putExtra("ID", assessment.getId());
        intent.putExtra("TITLE", assessment.getTitle());
        intent.putExtra("TEST", assessment.getAssessmentType());
        intent.putExtra("START", DateConverter.ToTimestamp(assessment.getStartDate()));
        intent.putExtra("END", DateConverter.ToTimestamp(assessment.getEndDate()));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which app bar item was chosen
        switch (item.getItemId()) {
            case R.id.edit:
                editItem();
                return true;
            case R.id.delete:
                deleteItem();
                return true;
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

    private void editItem() {
        Intent intent = new Intent(this, EditCourse.class);
        intent.putExtra("ID", PK);
        intent.putExtra("TITLE", title.getText());
        intent.putExtra("START", DateConverter.ToTimestamp(startDate));
        intent.putExtra("END", DateConverter.ToTimestamp(endDate));
        intent.putExtra("STATUS", status.getText());
        intent.putExtra("NAME", name.getText());
        intent.putExtra("PHONE", phone.getText());
        intent.putExtra("EMAIL", email.getText());
        intent.putExtra("FK", FK);
        startActivity(intent);
    }

    private void deleteItem() {
        if (courseById.hasObservers()) {
            courseById.removeObservers(DetailedCourse.this);
            CourseViewModel.delete(new Course(PK, title.toString(), startDate, endDate, status.toString(), name.toString(), phone.toString(), email.toString(), FK));
            finish();
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