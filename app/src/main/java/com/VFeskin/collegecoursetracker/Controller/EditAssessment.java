package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.CustomTextWatcher;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
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
 * This class is used for updating a assessment.
 * Displays a form that the user can make changes to and,
 * uses the information entered to update the assessment object/entity.
 */
public class EditAssessment extends AppCompatActivity {

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
    private Button updateAssessmentButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateDialog;
    private TimePickerDialog.OnTimeSetListener timeDialog;
    private Date startDate;
    private Long startTime;
    private Date startDateTime;

    // keys
    private int PK;
    private int FK;

    // check dropdown selection
    private String testType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        // set the field values to the xml ids
        assessmentTitleTxt = findViewById(R.id.editTextUpdateAssessmentTitle);
        autoCompleteTextView = findViewById(R.id.updateAssessmentDropdownText);
        startDateTxt = findViewById(R.id.editTextUpdateAssessmentStartDate);
        startTimeTxt = findViewById(R.id.editTextUpdateAssessmentStartTime);
        titleLayout = findViewById(R.id.updateAssessmentTitleTextInputLayout);
        dropdownLayout = findViewById(R.id.updateAssessmentDropdownLayout);
        startDateLayout = findViewById(R.id.updateAssessmentStartDateTextInputLayout);
        startTimeLayout = findViewById(R.id.updateStartTimeTextInputLayout);
        updateAssessmentButton = findViewById(R.id.updateAssessmentButton);

        // custom text listener for clearing set errors
        assessmentTitleTxt.addTextChangedListener(new CustomTextWatcher(titleLayout));
        startDateTxt.addTextChangedListener(new CustomTextWatcher(startDateLayout));
        startTimeTxt.addTextChangedListener(new CustomTextWatcher(startTimeLayout));
        autoCompleteTextView.addTextChangedListener(new CustomTextWatcher(dropdownLayout));

        // get values and set text
        Bundle extra = getIntent().getExtras();
        PK = extra.getInt("ID");
        assessmentTitleTxt.setText(extra.getString("TITLE"));
        autoCompleteTextView.setText(extra.getString("TEST"));
        startDate = DateConverter.fromLongToDate(extra.getLong("START"));
        startTime = extra.getLong("START");
        startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        startTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime));
        FK = extra.getInt("FK");

        // set exposed dropdown with items
        arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, getResources().getStringArray(R.array.test_type));
        autoCompleteTextView.setAdapter(arrayAdapter);

        // collect the item selected, onItemClick
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            testType = (String) parent.getAdapter().getItem(position);
        });

        // shows the date picker, onClick
        startDateTxt.setOnClickListener(view -> new DatePickerDialog(EditAssessment.this, dateDialog,
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

        // gets the values from date picker, onDataSet
        dateDialog = (view, year, month, day) -> {
            calendar.set(year, month, day);
            startDate = calendar.getTime();
            // format the output the screen

            startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        };

        // gets the values from time picker, onTimeSet
        timeDialog = (view, hour, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            startTime = calendar.getTimeInMillis();
            // format the output the screen

            startTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime));
        };

        // collect input and update the assessment
        updateAssessmentButton.setOnClickListener(view -> {

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

            testType = autoCompleteTextView.getText().toString();
            startDateTime = DateTimeParser.parseDateTime(startDate, startTime);

            AssessmentViewModel.update(new Assessment(PK, testType, title, startDateTime, FK));
            finish();
        });

    }

}