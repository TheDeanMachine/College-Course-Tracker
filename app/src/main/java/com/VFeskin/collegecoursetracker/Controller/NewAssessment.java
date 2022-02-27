package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.R;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
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
    private EditText endDateTxt;
    private Spinner testTypeSpinner;
    private Button createAssessmentButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateDialog;
    DatePickerDialog.OnDateSetListener dateDialog2;
    private Date startDate;
    private Date endDate;

    // view model reference, gives access to all assessments
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);

        // set the field values to the xml ids
        assessmentTitleTxt = findViewById(R.id.editTextAssessmentTitle);
        startDateTxt = findViewById(R.id.editTextAssessmentStartDate);
        endDateTxt = findViewById(R.id.editTextAssessmentEndDate);
        testTypeSpinner = findViewById(R.id.testSpinner);
        createAssessmentButton = findViewById(R.id.createAssessmentButton);

        // creates an instance of view model to use
        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(NewAssessment.this
                .getApplication())
                .create(AssessmentViewModel.class);

        // shows the date picker, onClick
        startDateTxt.setOnClickListener(view -> new DatePickerDialog(NewAssessment.this, dateDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // shows the date picker, onClick
        endDateTxt.setOnClickListener(view -> new DatePickerDialog(NewAssessment.this, dateDialog2,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // gets the values from date picker, onDataSet
        dateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(year, month, day);
                startDate = calendar.getTime();
                // format the output the screen
                startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
            }
        };

        // gets the values from date picker, onDataSet
        dateDialog2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(year, month, day);
                endDate = calendar.getTime();
                // format the output the screen
                endDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));
            }
        };

        // collect input and create new assessment
        createAssessmentButton.setOnClickListener(view -> {
            //TODO : input validation

            String title = assessmentTitleTxt.getText().toString();
            String test = (String) testTypeSpinner.getSelectedItem();

            // TODO : get PK
            AssessmentViewModel.insert(new Assessment(test, title, startDate, endDate, 1));
        });

    }


}