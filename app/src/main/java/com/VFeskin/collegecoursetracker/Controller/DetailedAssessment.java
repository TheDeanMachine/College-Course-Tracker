package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;

/**
 * This class displays a selected assessment in detail.
 */
public class DetailedAssessment extends AppCompatActivity {

    // XML attributes
    private TextView title;
    private TextView testType;
    private TextView start;
    private TextView end;

    // data
    private int PK;
    private int FK;
    private Date startDate;
    private Date endDate;
    private LiveData<Assessment> assessmentByID;

    // view model
    private AssessmentViewModel assessmentViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment);
        title = findViewById(R.id.textViewDetailAssessmentTitle);
        testType = findViewById(R.id.textViewDetailAssessmentType);
        start = findViewById(R.id.textViewDetailAssessmentStartDate);
        end = findViewById(R.id.textViewDetailAssessmentEndDate);

        PK = getIntent().getIntExtra("ID", 0);

        // creates an instance of view model to use
        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedAssessment.this
                .getApplication())
                .create(AssessmentViewModel.class);

        assessmentByID = assessmentViewModel.getByAssessmentsPK(PK);
        assessmentByID.observe(this, assessment -> {
            title.setText(assessment.getTitle());
            startDate = assessment.getStartDate();
            endDate = assessment.getEndDate();
            start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(assessment.getStartDate()));
            end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(assessment.getEndDate()));
            testType.setText(assessment.getAssessmentType());
            FK = assessment.getCourseId();
        });
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
        Intent intent = new Intent(this, EditAssessment.class);
        intent.putExtra("ID", PK);
        intent.putExtra("TITLE", title.getText());
        intent.putExtra("START", DateConverter.ToTimestamp(startDate));
        intent.putExtra("END", DateConverter.ToTimestamp(endDate));
        intent.putExtra("TEST", testType.getText());
        intent.putExtra("FK", FK);
        startActivity(intent);
    }

    private void deleteItem() {
        if (assessmentByID.hasObservers()) {
            assessmentByID.removeObservers(DetailedAssessment.this);
            AssessmentViewModel.delete(new Assessment(PK, testType.toString(), title.toString(), startDate, endDate, FK));
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