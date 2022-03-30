package com.VFeskin.collegecoursetracker.Controller;

import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.CustomTextWatcher;
import com.VFeskin.collegecoursetracker.Utility.DateTimeParser;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    private EditText startTimeTxt;
    private EditText endTimeTxt;
    private EditText roomNumberTxt;
    private EditText instructorNameTxt;
    private EditText instructorPhoneTxt;
    private EditText instructorEmailTxt;
    private TextInputLayout titleLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> arrayAdapter;
    private TextInputLayout dropdownLayout;
    private TextInputLayout startDateLayout;
    private TextInputLayout endDateLayout;
    private TextInputLayout startTimeLayout;
    private TextInputLayout endTimeLayout;
    private TextInputLayout roomNumLayout;
    private TextInputLayout instructorNameLayout;
    private TextInputLayout instructorPhoneLayout;
    private TextInputLayout instructorEmailLayout;
    private Button createCourseButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateDialog;
    private DatePickerDialog.OnDateSetListener dateDialog2;
    private TimePickerDialog.OnTimeSetListener timeDialog;
    private TimePickerDialog.OnTimeSetListener timeDialog2;
    private Date startDate;
    private Date endDate;
    private Long startTime;
    private Long endTime;
    private Date startDateTime;
    private Date endDateTime;

    // term PK
    private int id;

    // check dropdown selection
    private boolean isSelect = false;
    private String status = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        // set the field values to the xml ids
        courseTitleTxt = findViewById(R.id.editTextCourseTitle);
        autoCompleteTextView = findViewById(R.id.dropdownText);
        startDateTxt = findViewById(R.id.editTextCourseStartDate);
        endDateTxt = findViewById(R.id.editTextCourseEndDate);
        startTimeTxt = findViewById(R.id.editTextCourseStartTime);
        endTimeTxt = findViewById(R.id.editTextCourseEndTime);
        roomNumberTxt = findViewById(R.id.editTextCourseRoomNumber);
        instructorNameTxt = findViewById(R.id.editTextInstructorName);
        instructorPhoneTxt = findViewById(R.id.editTextInstructorPhone);
        instructorEmailTxt = findViewById(R.id.editTextInstructorEmailAddress);
        titleLayout = findViewById(R.id.newCourseTitleTextInputLayout);
        dropdownLayout = findViewById(R.id.dropdownLayout);
        startDateLayout = findViewById(R.id.newStartDateTextInputLayout);
        endDateLayout = findViewById(R.id.newEndDateTextInputLayout);
        startTimeLayout = findViewById(R.id.newStartTimeTextInputLayout);
        endTimeLayout = findViewById(R.id.newEndTimeTextInputLayout);
        roomNumLayout = findViewById(R.id.newCourseRoomTextInputLayout);
        instructorNameLayout = findViewById(R.id.newInstructorNameTextInputLayout);
        instructorPhoneLayout = findViewById(R.id.newInstructorPhoneTextInputLayout);
        instructorEmailLayout = findViewById(R.id.newInstructorEmailTextInputLayout);
        createCourseButton = findViewById(R.id.createCourseButton);

        // custom text listener for clearing set errors
        courseTitleTxt.addTextChangedListener(new CustomTextWatcher(titleLayout));
        startDateTxt.addTextChangedListener(new CustomTextWatcher(startDateLayout));
        endDateTxt.addTextChangedListener(new CustomTextWatcher(endDateLayout));
        startTimeTxt.addTextChangedListener(new CustomTextWatcher(startTimeLayout));
        endTimeTxt.addTextChangedListener(new CustomTextWatcher(endTimeLayout));
        roomNumberTxt.addTextChangedListener(new CustomTextWatcher(roomNumLayout));
        instructorNameTxt.addTextChangedListener(new CustomTextWatcher(instructorNameLayout));
        instructorPhoneTxt.addTextChangedListener(new CustomTextWatcher(instructorPhoneLayout));
        instructorEmailTxt.addTextChangedListener(new CustomTextWatcher(instructorEmailLayout));
        autoCompleteTextView.addTextChangedListener(new CustomTextWatcher(dropdownLayout));

//        String[] courseStatus = getResources().getStringArray(R.array.course_status);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, courseStatus);
//        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.dropdownText);
//        autoCompleteTextView.setAdapter(arrayAdapter);

        // set exposed dropdown with items
        arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, getResources().getStringArray(R.array.course_status));
        autoCompleteTextView.setAdapter(arrayAdapter);

        // collect the item selected, onItemClick
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            status = (String) parent.getAdapter().getItem(position);
            isSelect = true;
        });

        // shows the time picker, onClick
        startTimeTxt.setOnClickListener(view -> new TimePickerDialog(this, timeDialog,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false)
                .show());

        // shows the time picker, onClick
        endTimeTxt.setOnClickListener(view -> new TimePickerDialog(this, timeDialog2,
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

        // gets the values from time picker, onTimeSet
        timeDialog2 = (view, hour, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            endTime = calendar.getTimeInMillis();

            // format the output the screen
            endTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(endTime));
        };

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
            startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        };

        // gets the values from date picker, onDataSet
        dateDialog2 = (view, year, month, day) -> {
            calendar.set(year, month, day);
            endDate = calendar.getTime();

            // format the output the screen
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
                titleLayout.setError("Title is required!");
                Snackbar.make(view, "Please enter a title", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (startDate == null || startDateTxt.getText().toString().isEmpty()) {
                startDateLayout.setError("Start date is required!");
                Snackbar.make(view, "Please enter a date", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (endDate == null || endDateTxt.getText().toString().isEmpty()) {
                endDateLayout.setError("End date is required!");
                Snackbar.make(view, "Please enter a date", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (startTime == null || startTimeTxt.getText().toString().isEmpty()) {
                startTimeLayout.setError("Time is required!");
                Snackbar.make(view, "Please enter course start time", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (endTime == null || endTimeTxt.getText().toString().isEmpty()) {
                endTimeLayout.setError("Time is required!");
                Snackbar.make(view, "Please enter course end time", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String room = null;
            try {
                room = roomNumberTxt.getText().toString();
                if(room == null || room.isEmpty()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                roomNumLayout.setError("Room number is required!");
                Snackbar.make(view, "Please enter the room number or course number", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String name = null;
            try {
                name = instructorNameTxt.getText().toString();
                if(name == null || name.isEmpty()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                instructorNameLayout.setError("Name is required!");
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
                instructorPhoneLayout.setError("Phone is required!");
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
                instructorEmailLayout.setError("Email is required!");
                Snackbar.make(view, "Please enter the instructor email", Snackbar.LENGTH_SHORT).show();
                return;
            }

            try {
//                status = autoCompleteTextView.getText().toString();
                if (!isSelect) {
                    throw new Exception();
                }
            } catch (Exception e) {
                dropdownLayout.setError("Status is required!");
                Snackbar.make(view, "Please select course status", Snackbar.LENGTH_SHORT).show();
                return;
            }

            startDateTime = DateTimeParser.parseDateTime(startDate, startTime);
            endDateTime = DateTimeParser.parseDateTime(endDate, endTime);
            id = getIntent().getIntExtra("ID", 0);

            CourseViewModel.insert(new Course(title, startDateTime, endDateTime, name, phone, email, status, room, id));
            finish();
        });

    }

    // saves the date on orientation change
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("START", startDate);
        outState.putSerializable("END", endDate);
        outState.putSerializable("START_TIME", startTime);
        outState.putSerializable("END_TIME", startTime);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        startDate = (Date) savedInstanceState.getSerializable("START");
        endDate = (Date) savedInstanceState.getSerializable("END");
        startTime = (Long) savedInstanceState.getSerializable("START_TIME");
        endTime = (Long) savedInstanceState.getSerializable("END_TIME");
    }

}