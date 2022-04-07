package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
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
 * This class is used for updating a course.
 * Displays a form that the user can make changes to and,
 * uses the information entered to update the course object/entity.
 */
public class EditCourse extends AppCompatActivity {

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
    private Button updateCourseButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener startDateDialog;
    private DatePickerDialog.OnDateSetListener endDateDialog;
    private TimePickerDialog.OnTimeSetListener startTimeDialog;
    private TimePickerDialog.OnTimeSetListener endTimeDialog;
    private Date startDate;
    private Date endDate;
    private Long startTime;
    private Long endTime;
    private Date startDateTime;
    private Date endDateTime;

    // keys
    private int PK;
    private int FK;

    // check dropdown selection
    private String status = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        // set the field values to the xml ids
        courseTitleTxt = findViewById(R.id.editTextUpdateCourseTitle);
        autoCompleteTextView = findViewById(R.id.updateDropdownText);
        startDateTxt = findViewById(R.id.editTextUpdateCourseStartDate);
        endDateTxt = findViewById(R.id.editTextUpdateCourseEndDate);
        startTimeTxt = findViewById(R.id.editTextUpdateCourseStartTime);
        endTimeTxt = findViewById(R.id.editTextUpdateCourseEndTime);
        roomNumberTxt = findViewById(R.id.editTextUpdateCourseRoomNumber);
        instructorNameTxt = findViewById(R.id.editTextUpdateInstructorName);
        instructorPhoneTxt = findViewById(R.id.editTextUpdateInstructorPhone);
        instructorEmailTxt = findViewById(R.id.editTextUpdateInstructorEmailAddress);
        titleLayout = findViewById(R.id.updateCourseTitleTextInputLayout);
        dropdownLayout = findViewById(R.id.updateDropdownLayout);
        startDateLayout = findViewById(R.id.updateStartDateTextInputLayout);
        endDateLayout = findViewById(R.id.updateEndDateTextInputLayout);
        startTimeLayout = findViewById(R.id.updateStartTimeTextInputLayout);
        endTimeLayout = findViewById(R.id.updateEndTimeTextInputLayout);
        roomNumLayout = findViewById(R.id.updateCourseRoomTextInputLayout);
        instructorNameLayout = findViewById(R.id.updateInstructorNameTextInputLayout);
        instructorPhoneLayout = findViewById(R.id.updateInstructorPhoneTextInputLayout);
        instructorEmailLayout = findViewById(R.id.updateInstructorEmailTextInputLayout);
        updateCourseButton = findViewById(R.id.updateCourseButton);

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

        // get values and set text
        Bundle extra = getIntent().getExtras();
        PK = extra.getInt("ID");
        courseTitleTxt.setText(extra.getString("TITLE"));
        autoCompleteTextView.setText(extra.getString("STATUS"));
//        courseStatusSpinner.setSelection(((ArrayAdapter<String>)courseStatusSpinner.getAdapter()).getPosition(extra.getString("STATUS")));
        startDate = DateConverter.fromLongToDate(extra.getLong("START"));
        startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        endDate = DateConverter.fromLongToDate(extra.getLong("END"));
        endDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));
        startTime = startDate.getTime();
        startTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime));
        endTime = endDate.getTime();
        endTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(endTime));
        roomNumberTxt.setText(extra.getString("ROOM"));
        instructorNameTxt.setText(extra.getString("NAME"));
        instructorPhoneTxt.setText(extra.getString("PHONE"));
        instructorEmailTxt.setText(extra.getString("EMAIL"));
        FK = extra.getInt("FK");

        // set exposed dropdown with items
        arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, getResources().getStringArray(R.array.course_status));
        autoCompleteTextView.setAdapter(arrayAdapter);

        // collect the item selected, onItemClick
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            status = (String) parent.getAdapter().getItem(position);
        });

        // shows the time picker, onClick
        startTimeTxt.setOnClickListener(view -> new TimePickerDialog(this, startTimeDialog,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false)
                .show());

        // shows the time picker, onClick
        endTimeTxt.setOnClickListener(view -> new TimePickerDialog(this, endTimeDialog,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false)
                .show());

        // gets the values from time picker, onTimeSet
        startTimeDialog = (view, hour, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            startTime = calendar.getTimeInMillis();

            // format the output the screen
            startTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime));
        };

        // gets the values from time picker, onTimeSet
        endTimeDialog = (view, hour, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            endTime = calendar.getTimeInMillis();

            // format the output the screen
            endTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(endTime));
        };

        // shows the date picker, onClick
        startDateTxt.setOnClickListener(view -> new DatePickerDialog(EditCourse.this, startDateDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // shows the date picker, onClick
        endDateTxt.setOnClickListener(view -> new DatePickerDialog(EditCourse.this, endDateDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // gets the values from date picker, onDataSet
        startDateDialog = (view, year, month, day) -> {
            calendar.set(year, month, day);
            startDate = calendar.getTime();
            // format the output the screen
            startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        };

        // gets the values from date picker, onDataSet
        endDateDialog = (view, year, month, day) -> {
            calendar.set(year, month, day);
            endDate = calendar.getTime();
            // format the output the screen
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

            status = autoCompleteTextView.getText().toString();
            startDateTime = DateTimeParser.parseDateTime(startDate, startTime);
            endDateTime = DateTimeParser.parseDateTime(endDate, endTime);

            CourseViewModel.update(new Course(PK, title, startDateTime, endDateTime, name, phone, email, status, room, FK));
            finish();
        });

    }

}