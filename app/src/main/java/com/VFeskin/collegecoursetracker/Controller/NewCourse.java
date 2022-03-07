package com.VFeskin.collegecoursetracker.Controller;

import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.Status;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.app.DatePickerDialog;
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

    // view model reference, gives access to all courses
    private CourseViewModel courseViewModel;

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

        // create an array with Enum values for the spinner to hold
        courseStatusSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Status.values()));

        instructorNameTxt = findViewById(R.id.editTextInstructorName);
        instructorPhoneTxt = findViewById(R.id.editTextInstructorPhone);
        instructorEmailTxt = findViewById(R.id.editTextInstructorEmailAddress);
        createCourseButton = findViewById(R.id.createCourseButton);

        // creates an instance of view model to use
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(NewCourse.this
                .getApplication())
                .create(CourseViewModel.class);

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

        // collect input and create new course
        createCourseButton.setOnClickListener(view -> {

            //TODO : input validation
            String title = courseTitleTxt.getText().toString();
            //TODO : change back to using string not UNUM
            Status status = (Status) courseStatusSpinner.getSelectedItem();
            String name = instructorNameTxt.getText().toString();
            String phone = instructorPhoneTxt.getText().toString();
            String email = instructorEmailTxt.getText().toString();
            id = getIntent().getIntExtra("ID", 0);

            CourseViewModel.insert(new Course(title, startDate, endDate, name, phone, email, status, id));
        });

    }
}