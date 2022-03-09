package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.Model.TermViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.google.android.material.snackbar.Snackbar;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is used for updating a term.
 * Displays a form that the user can fill out, uses the information entered
 * to update a new term object/entity.
 */
public class EditTerm extends AppCompatActivity {

    // XML attributes
    private EditText termTitleTxt;
    private EditText startDateTxt;
    private EditText endDateTxt;
    private Button updateTerm;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateDialog;
    DatePickerDialog.OnDateSetListener dateDialog2;
    private Date startDate;
    private Date endDate;

    // term PK
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);

        // set the field values to the xml ids
        termTitleTxt = findViewById(R.id.editTextUpdateTermTitle);
        startDateTxt = findViewById(R.id.editTextUpdateTermStartDate);
        endDateTxt = findViewById(R.id.editTextUpdateTermEndDate);
        updateTerm = findViewById(R.id.UpdateTermTermButton);

        // get values from term card and set text
        Bundle extra = getIntent().getExtras();
        id = extra.getInt("ID");
        termTitleTxt.setText(extra.getString("TITLE"));
        startDate = DateConverter.fromTimestamp(extra.getLong("START"));
        startDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        endDate = DateConverter.fromTimestamp(extra.getLong("END"));
        endDateTxt.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));

        // shows the date picker, onClick
        startDateTxt.setOnClickListener(view -> new DatePickerDialog(EditTerm.this, dateDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show());

        // shows the date picker, onClick
        endDateTxt.setOnClickListener(view -> new DatePickerDialog(EditTerm.this, dateDialog2,
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

        // collect the input and update the term
        updateTerm.setOnClickListener(view -> {
            // Input validation
            String title = null;
            try {
                title = termTitleTxt.getText().toString();
                if(title == null || title.isEmpty()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                termTitleTxt.setError("Title is required!");
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

            TermViewModel.update(new Term(id, title, startDate, endDate));
            finish();
        });

    }
}