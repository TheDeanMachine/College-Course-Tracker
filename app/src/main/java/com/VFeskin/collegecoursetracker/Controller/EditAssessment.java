package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.google.android.material.snackbar.Snackbar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private Spinner testTypeSpinner;
    private Button updateAssessmentButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateDialog;
    TimePickerDialog.OnTimeSetListener timeDialog;
    private Date startDate;
    private Long startTime;

    // keys
    private int PK;
    private int FK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        // set the field values to the xml ids
        assessmentTitleTxt = findViewById(R.id.editTextUpdateAssessmentTitle);
        startDateTxt = findViewById(R.id.editTextUpdateAssessmentStartDate);
        startTimeTxt = findViewById(R.id.editTextUpdateAssessmentStartTime);
        testTypeSpinner = findViewById(R.id.updateAssessmentSpinner);
        updateAssessmentButton = findViewById(R.id.updateAssessmentButton);

        // get values and set text
        Bundle extra = getIntent().getExtras();
        PK = extra.getInt("ID");
        assessmentTitleTxt.setText(extra.getString("TITLE"));
        startDate = DateConverter.fromTimestamp(extra.getLong("START"));
        startTime = extra.getLong("START");
        startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        startTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime));
        testTypeSpinner.setSelection(((ArrayAdapter<String>)testTypeSpinner.getAdapter()).getPosition(extra.getString("TEST")));
        FK = extra.getInt("FK");

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
            startDateTxt.setError(null); // clears set error
            startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        };

        // gets the values from time picker, onTimeSet
        timeDialog = (view, hour, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            startTime = calendar.getTimeInMillis();
            // format the output the screen
            startTimeTxt.setError(null); // clears set error
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

            startDate.setTime(startTime); // set with user time

            AssessmentViewModel.update(new Assessment(PK, test, title, startDate, FK));
            finish();
        });

    }

}