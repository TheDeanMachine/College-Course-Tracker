package com.VFeskin.collegecoursetracker.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.Model.TermViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.CustomTextWatcher;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is used for adding a new term.
 * Displays a form that the user can fill out, uses the information entered
 * to create a new term object/entity and add it into the database.
 */
public class NewTerm extends AppCompatActivity {

    // XML attributes
    private EditText termTitleTxt;
    private EditText startDateTxt;
    private EditText endDateTxt;
    private TextInputLayout titleLayout;
    private TextInputLayout startLayout;
    private TextInputLayout endLayout;
    private Button createTermButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateDialog;
    DatePickerDialog.OnDateSetListener dateDialog2;
    private Date startDate;
    private Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);

        // set the field values to the xml ids
        termTitleTxt = findViewById(R.id.editTextTermTitle);
        startDateTxt = findViewById(R.id.editTextStartDate);
        endDateTxt = findViewById(R.id.editTextEndDate);
        titleLayout = findViewById(R.id.newTitleTextInputLayout);
        startLayout = findViewById(R.id.newStartTextInputLayout);
        endLayout = findViewById(R.id.newEndTextInputLayout);
        createTermButton = findViewById(R.id.createTermButton);

        // title text listener
        termTitleTxt.addTextChangedListener(new CustomTextWatcher(titleLayout));

        // shows the date picker, onClick
        startDateTxt.setOnClickListener(view -> new DatePickerDialog(NewTerm.this, dateDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // shows the date picker, onClick
        endDateTxt.setOnClickListener(view -> new DatePickerDialog(NewTerm.this, dateDialog2,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // gets the values from date picker, onDataSet
        dateDialog = (view, year, month, day) -> {
            calendar.set(year, month, day);
            startDate = calendar.getTime();
            // format the output the screen
            startLayout.setError(null); // clears set error
            startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        };

        // gets the values from date picker, onDataSet
        dateDialog2 = (view, year, month, day) -> {
            calendar.set(year, month, day);
            endDate = calendar.getTime();
            // format the output the screen
            endLayout.setError(null); // clears set error
            endDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));
        };


        // creates the term
        createTermButton.setOnClickListener(view -> {
            // Input validation
            String title = null;
            try {
                title = termTitleTxt.getText().toString();
                if(title == null || title.isEmpty()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                titleLayout.setError("Title is required!");
                Snackbar.make(view, "Please enter a title", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (startDate == null || startDateTxt.getText().toString().isEmpty()) {
                startLayout.setError("Start date is required!");

                Snackbar.make(view, "Please enter a date", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (endDate == null || endDateTxt.getText().toString().isEmpty()) {
                endLayout.setError("End date is required!");
                Snackbar.make(view, "Please enter a date", Snackbar.LENGTH_SHORT).show();
                return;
            }

            TermViewModel.insert(new Term(title, startDate, endDate));
            finish();
        });

    }

    // saves the date on orientation change
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("START", startDate);
        outState.putSerializable("END", endDate);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        startDate = (Date) savedInstanceState.getSerializable("START");
        endDate = (Date) savedInstanceState.getSerializable("END");
    }
}