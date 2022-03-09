package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.google.android.material.snackbar.Snackbar;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is used for updating a course.
 * Displays a form that the user can make changes to and,
 * uses the information entered to update the course object/entity.
 */
public class EditCourse extends AppCompatActivity {

    // XML attributes
    private EditText courseTitleTxt;
    private EditText startDateTxt;
    private EditText endDateTxt;
    private Spinner courseStatusSpinner;
    private EditText instructorNameTxt;
    private EditText instructorPhoneTxt;
    private EditText instructorEmailTxt;
    private Button updateCourseButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateDialog;
    DatePickerDialog.OnDateSetListener dateDialog2;
    private Date startDate;
    private Date endDate;

    // keys
    private int PK;
    private int FK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        // set the field values to the xml ids
        courseTitleTxt = findViewById(R.id.editTextUpdateCourseTitle);
        startDateTxt = findViewById(R.id.editTextUpdateCourseStartDate);
        endDateTxt = findViewById(R.id.editTextUpdateCourseEndDate);
        courseStatusSpinner = findViewById(R.id.courseUpdateSpinner);
        instructorNameTxt = findViewById(R.id.editTextUpdateInstructorName);
        instructorPhoneTxt = findViewById(R.id.editTextUpdateInstructorPhone);
        instructorEmailTxt = findViewById(R.id.editTextUpdateInstructorEmailAddress);
        updateCourseButton = findViewById(R.id.updateCourseButton);

        // get values from term card and set text
        Bundle extra = getIntent().getExtras();
        FK = extra.getInt("ID");
        courseTitleTxt.setText(extra.getString("TITLE"));
        startDate = DateConverter.fromTimestamp(extra.getLong("START"));
        startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        endDate = DateConverter.fromTimestamp(extra.getLong("END"));
        endDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));
//        courseStatusSpinner.setSelection(  courseStatusSpinner.getPosition(extra.getString("STATUS")));
        instructorNameTxt.setText(extra.getString("NAME"));
        instructorPhoneTxt.setText(extra.getString("PHONE"));
        instructorEmailTxt.setText(extra.getString("EMAIL"));

        // shows the date picker, onClick
        startDateTxt.setOnClickListener(view -> new DatePickerDialog(EditCourse.this, dateDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // shows the date picker, onClick
        endDateTxt.setOnClickListener(view -> new DatePickerDialog(EditCourse.this, dateDialog2,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // gets the values from date picker, onDataSet
        dateDialog = (view, year, month, day) -> {
            calendar.set(year, month, day);
            startDate = calendar.getTime();
            // format the output the screen
            startDateTxt.setError(null); // clears set error
            startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        };

        // gets the values from date picker, onDataSet
        dateDialog2 = (view, year, month, day) -> {
            calendar.set(year, month, day);
            endDate = calendar.getTime();
            // format the output the screen
            endDateTxt.setError(null); // clears set error
            endDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));
        };

        // collect input and update the course
        updateCourseButton.setOnClickListener(view -> {

            // Input validation
            String title = null;
            try {
                title = courseTitleTxt.getText().toString();
                if(title == null || title.isEmpty()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                courseTitleTxt.setError("Title is required!");
                Snackbar.make(view, "Please enter a title", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (startDate == null || startDateTxt.getText().toString().isEmpty()) {
                startDateTxt.setError("Start date is required!");
                Snackbar.make(view, "Please enter a date", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (endDate == null || endDateTxt.getText().toString().isEmpty()) {
                endDateTxt.setError("End date is required!");
                Snackbar.make(view, "Please enter a date", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String name = null;
            try {
                name = instructorNameTxt.getText().toString();
                if(name == null || name.isEmpty()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                instructorNameTxt.setError("Name is required!");
                Snackbar.make(view, "Please enter the instructor name", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String phone = null;
            try {
                phone = instructorPhoneTxt.getText().toString();
                if(phone == null || phone.isEmpty()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                instructorPhoneTxt.setError("Phone is required!");
                Snackbar.make(view, "Please enter the instructor phone", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String email = null;
            try {
                email = instructorEmailTxt.getText().toString();
                if(email == null || email.isEmpty()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                instructorEmailTxt.setError("Email is required!");
                Snackbar.make(view, "Please enter the instructor email", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String status = null;
            try {
                status = courseStatusSpinner.getSelectedItem().toString();
                if (status == null || courseStatusSpinner.getSelectedItem().equals("Select status")) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Snackbar.make(view, "Please select course status", Snackbar.LENGTH_SHORT).show();
                return;
            }



//            CourseViewModel.update(new Course(title, startDate, endDate, name, phone, email, status, id));
//            finish();
        });

    }







}