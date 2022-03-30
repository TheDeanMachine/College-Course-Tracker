package com.VFeskin.collegecoursetracker.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.CustomTextWatcher;
import com.VFeskin.collegecoursetracker.Utility.DateTimeParser;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText startTimeTxt;
    private TextInputLayout titleLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> arrayAdapter;
    private TextInputLayout dropdownLayout;
    private TextInputLayout startDateLayout;
    private TextInputLayout startTimeLayout;
    private Button createAssessmentButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateDialog;
    private TimePickerDialog.OnTimeSetListener timeDialog;
    private Date startDate;
    private Long startTime;
    private Date startDateTime;

    // course PK
    private int id;

    // check dropdown selection
    private boolean isSelect = false;
    private String testType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);

        // set the field values to the xml ids
        assessmentTitleTxt = findViewById(R.id.editTextAssessmentTitle);
        autoCompleteTextView = findViewById(R.id.newAssessmentDropdownText);
        startDateTxt = findViewById(R.id.editTextAssessmentStartDate);
        startTimeTxt = findViewById(R.id.editTextAssessmentStarTime);
        titleLayout = findViewById(R.id.newAssessmentTitleTextInputLayout);
        dropdownLayout = findViewById(R.id.newAssessmentDropdownLayout);
        startDateLayout = findViewById(R.id.newAssessmentStartDateTextInputLayout);
        startTimeLayout = findViewById(R.id.newStartTimeTextInputLayout);
        createAssessmentButton = findViewById(R.id.createAssessmentButton);

        // custom text listener for clearing set errors
        assessmentTitleTxt.addTextChangedListener(new CustomTextWatcher(titleLayout));
        startDateTxt.addTextChangedListener(new CustomTextWatcher(startDateLayout));
        startTimeTxt.addTextChangedListener(new CustomTextWatcher(startTimeLayout));
        autoCompleteTextView.addTextChangedListener(new CustomTextWatcher(dropdownLayout));

        // set exposed dropdown with items
        arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, getResources().getStringArray(R.array.test_type));
        autoCompleteTextView.setAdapter(arrayAdapter);

        // collect the item selected, onItemClick
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            testType = (String) parent.getAdapter().getItem(position);
            isSelect = true;
        });

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

            startTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime));
        };

        // gets the values from date picker, onDataSet
        dateDialog = (view, year, month, day) -> {
            calendar.set(year, month, day);
            startDate = calendar.getTime();
            // format the output the screen

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
                titleLayout.setError("Title is required!");
                Snackbar.make(view, "Please enter assessment title", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (startDate == null || startDateTxt.getText().toString().isEmpty()) {
                startDateLayout.setError("Start date is required!");
                Snackbar.make(view, "Please enter assessment date", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (startTime == null || startTimeTxt.getText().toString().isEmpty()) {
                startTimeLayout.setError("Time is required!");
                Snackbar.make(view, "Please enter assessment time", Snackbar.LENGTH_SHORT).show();
                return;
            }


            try {
                if (!isSelect) {
                    throw new Exception();
                }
            } catch (Exception e) {
                dropdownLayout.setError("Status is required!");
                Snackbar.make(view, "Please select assessment type", Snackbar.LENGTH_SHORT).show();
                return;
            }

            id = getIntent().getIntExtra("ID", 0); // FK
            startDateTime = DateTimeParser.parseDateTime(startDate, startTime);

            AssessmentViewModel.insert(new Assessment(testType, title, startDateTime, id));
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