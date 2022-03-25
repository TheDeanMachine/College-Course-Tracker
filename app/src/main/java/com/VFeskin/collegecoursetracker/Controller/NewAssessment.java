package com.VFeskin.collegecoursetracker.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateTimeParser;
import com.google.android.material.snackbar.Snackbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is used for adding a new assessment.
 * Displays a form that the user can fill out, uses the information entered
 * to create a new assessment object/entity and adding it into the database.
 */
public class NewAssessment extends AppCompatActivity {

    // XML attributes
    private EditText assessmentTitleTxt;
    private EditText startDateTxt;
    private EditText startTimeTxt;
    private Spinner testTypeSpinner;
    private Button createAssessmentButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateDialog;
    TimePickerDialog.OnTimeSetListener timeDialog;
    private Date startDate;
    private Long startTime;
    private Date startDateTime;

    // course PK
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);

        // set the field values to the xml ids
        assessmentTitleTxt = findViewById(R.id.editTextAssessmentTitle);
        startDateTxt = findViewById(R.id.editTextAssessmentStartDate);
        startTimeTxt = findViewById(R.id.editTextAssessmentStarTime);
        testTypeSpinner = findViewById(R.id.testSpinner);
        createAssessmentButton = findViewById(R.id.createAssessmentButton);

        // shows the date picker, onClick
        startDateTxt.setOnClickListener(view -> new DatePickerDialog(NewAssessment.this, dateDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // shows the time picker, onClick
        startTimeTxt.setOnClickListener(view -> new TimePickerDialog(this, timeDialog,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false)
                .show());

        // gets the values from time picker, onTimeSet
        timeDialog = (view, hour, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            startTime = calendar.getTimeInMillis();
            // format the output the screen
            startTimeTxt.setError(null); // clears set error
            startTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime));
        };

        // gets the values from date picker, onDataSet
        dateDialog = (view, year, month, day) -> {
            calendar.set(year, month, day);
            startDate = calendar.getTime();
            // format the output the screen
            startDateTxt.setError(null); // clears set error
            startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        };

        // collect input and create new assessment
        createAssessmentButton.setOnClickListener(view -> {

            // Input validation
            String title = null;
            try {
                title = assessmentTitleTxt.getText().toString();
                if(title == null || title.isEmpty()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                assessmentTitleTxt.setError("Title is required!");
                Snackbar.make(view, "Please enter assessment title", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (startDate == null || startDateTxt.getText().toString().isEmpty()) {
                startDateTxt.setError("Start date is required!");
                Snackbar.make(view, "Please enter assessment date", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (startTime == null || startTimeTxt.getText().toString().isEmpty()) {
                startTimeTxt.setError("Time is required!");
                Snackbar.make(view, "Please enter assessment time", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String test = null;
            try {
                test = testTypeSpinner.getSelectedItem().toString();
                if (test == null || testTypeSpinner.getSelectedItem().equals("Select assessment type")) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Snackbar.make(view, "Please select assessment type", Snackbar.LENGTH_SHORT).show();
                return;
            }

            id = getIntent().getIntExtra("ID", 0); // FK
            startDateTime = DateTimeParser.parseDateTime(startDate, startTime);

            AssessmentViewModel.insert(new Assessment(test, title, startDateTime, id));
            finish();
        });

    }

    // saves the date on orientation change
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("START", startDate);
        outState.putSerializable("TIME", startTime);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        startDate = (Date) savedInstanceState.getSerializable("START");
        startTime = (Long) savedInstanceState.getSerializable("TIME");
    }

}