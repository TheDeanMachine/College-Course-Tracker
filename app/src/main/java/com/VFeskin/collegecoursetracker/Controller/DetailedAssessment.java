package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.VFeskin.collegecoursetracker.Utility.DateReceiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * This class displays a selected assessment in detail.
 */
public class DetailedAssessment extends AppCompatActivity {

    // XML attributes
    private TextView title;
    private TextView testType;
    private TextView start;
//    private TextView end;

    // data
    private int PK;
    private int FK;
    private Date startDateTime;
//    private Date endDate;
    private LiveData<Assessment> assessmentByID;

    // view model
    private AssessmentViewModel assessmentViewModel;

    // add button
    private FloatingActionButton fabAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._temp_);
        title = findViewById(R.id.textViewDetailAssessmentTitle);
        testType = findViewById(R.id.textViewDetailAssessmentType);
        start = findViewById(R.id.textViewDetailAssessmentStartDate);
//        end = findViewById(R.id.textViewDetailAssessmentEndDate);
        fabAlarm = findViewById(R.id.add_test_alarm);

        PK = getIntent().getIntExtra("ID", 0);

        // creates an instance of view model to use
        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedAssessment.this
                .getApplication())
                .create(AssessmentViewModel.class);

        assessmentByID = assessmentViewModel.getByAssessmentsPK(PK);
        assessmentByID.observe(this, assessment -> {
            title.setText(assessment.getTitle());
            startDateTime = assessment.getStartDateTime();
//            endDate = assessment.getEndDate();
            start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(assessment.getStartDateTime()));
//            end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(assessment.getEndDate()));
            testType.setText(assessment.getAssessmentType());
            FK = assessment.getCourseId();
        });

        // add alarm
        fabAlarm.setOnClickListener(this::addTestAlarm);
    }

    private void addTestAlarm(View view) {
        Long trigger = startDateTime.getTime();
        String title = Objects.requireNonNull(assessmentByID.getValue()).getTitle();

        Intent intent = new Intent(this, DateReceiver.class);
        intent.putExtra("KEY", "Assessment " + title + " starts today" );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, DateReceiver.numAlert++ ,intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);

        Snackbar.make(view, "Alarm for " + title + " set", Snackbar.LENGTH_LONG).show();
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
        intent.putExtra("START", DateConverter.ToTimestamp(startDateTime));
//        intent.putExtra("END", DateConverter.ToTimestamp(endDate));
        intent.putExtra("TEST", testType.getText());
        intent.putExtra("FK", FK);
        startActivity(intent);
    }

    private void deleteItem() {
        if (assessmentByID.hasObservers()) {
            assessmentByID.removeObservers(DetailedAssessment.this);
            AssessmentViewModel.delete(new Assessment(PK, testType.toString(), title.toString(), startDateTime, FK));
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