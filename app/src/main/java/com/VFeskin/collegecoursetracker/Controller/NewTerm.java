package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.Model.TermViewModel;
import com.VFeskin.collegecoursetracker.R;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is used for adding a new term.
 * Creates a form that the user fills out, and adds a Term object into DB.
 */
public class NewTerm extends AppCompatActivity {

    // XML attributes
    private EditText termTitleTxt;
    private EditText startDateTxt;
    private EditText endDateTxt;
    private Button createTermButton;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateDialog;
    DatePickerDialog.OnDateSetListener dateDialog2;
    private Date startDate;
    private Date endDate;

    // view model reference, gives access to all terms
    private TermViewModel termViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);

        // set the field values to the xml ids
        termTitleTxt = findViewById(R.id.editTextTermTitle);
        startDateTxt = findViewById(R.id.editTextStartDate);
        endDateTxt = findViewById(R.id.editTextEndDate);
        createTermButton = findViewById(R.id.createTermButton);

        // creates an instance of view model to use
        termViewModel = new ViewModelProvider.AndroidViewModelFactory(NewTerm.this
                .getApplication())
                .create(TermViewModel.class);

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
        dateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(year, month, day);
                startDate = calendar.getTime();
                startDateTxt.setText(startDate.toString());
            }
        };

        // gets the values from date picker, onDataSet
        dateDialog2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(year, month, day);
                endDate = calendar.getTime();
                endDateTxt.setText(endDate.toString());
            }
        };

        // creates the term
        createTermButton.setOnClickListener(view -> {
            String title = termTitleTxt.getText().toString();
            TermViewModel.insert(new Term(title, startDate, endDate));
        });

    }

}