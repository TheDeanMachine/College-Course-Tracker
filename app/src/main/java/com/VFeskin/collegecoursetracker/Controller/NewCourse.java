package com.VFeskin.collegecoursetracker.Controller;

import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.Status;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is used for adding a new course.
 * Displays a form that the user can fill out, uses the information entered
 * to create a new course object/entity and adding it into the database.
 */
public class NewCourse extends AppCompatActivity {

    // XML attributes
    private EditText courseTitleTxt;
    private EditText startDateTxt;
    private EditText endDateTxt;
    private Spinner courseStatusSpinner;
    private EditText instructorNameTxt;
    private EditText instructorPhoneTxt;
    private EditText instructorEmailTxt;
    private Button createCourseButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateDialog;
    DatePickerDialog.OnDateSetListener dateDialog2;
    private Date startDate;
    private Date endDate;

//    // view model reference, gives access to all courses
//    private CourseViewModel courseViewModel;

    // term PK
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        // set the field values to the xml ids
        courseTitleTxt = findViewById(R.id.editTextCourseTitle);
        startDateTxt = findViewById(R.id.editTextCourseStartDate);
        endDateTxt = findViewById(R.id.editTextCourseEndDate);
        courseStatusSpinner = findViewById(R.id.spinner);
        courseStatusSpinner.setPrompt("Please Select status");

//        // create an array with Enum values for the spinner to hold
//        courseStatusSpinner.setAdapter(new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, Status.values()));

        instructorNameTxt = findViewById(R.id.editTextInstructorName);
        instructorPhoneTxt = findViewById(R.id.editTextInstructorPhone);
        instructorEmailTxt = findViewById(R.id.editTextInstructorEmailAddress);
        createCourseButton = findViewById(R.id.createCourseButton);

        // creates an instance of view model to use
//        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(NewCourse.this
//                .getApplication())
//                .create(CourseViewModel.class);

        // shows the date picker, onClick
        startDateTxt.setOnClickListener(view -> new DatePickerDialog(NewCourse.this, dateDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // shows the date picker, onClick
        endDateTxt.setOnClickListener(view -> new DatePickerDialog(NewCourse.this, dateDialog2,
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

        // collect input and create new course
        createCourseButton.setOnClickListener(view -> {

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

            id = getIntent().getIntExtra("ID", 0);

            CourseViewModel.insert(new Course(title, startDate, endDate, name, phone, email, status, id));
            finish();
        });

    }

}