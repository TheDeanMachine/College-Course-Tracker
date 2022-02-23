package com.VFeskin.collegecoursetracker.Controller;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.Model.TermViewModel;
import com.VFeskin.collegecoursetracker.R;

import java.util.Calendar;
import java.util.Date;

public class NewTerm extends AppCompatActivity {

    // XML attributes
    private EditText termTitle;
    private EditText startDate;
    private EditText endDate;
    private Button createTerm;


    // viewM model reference, gives access to all terms
    private TermViewModel termViewModel;

    // date related fields
    private final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateDialog;

    private Date dateToSet;

    private EditText dateTextField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);

        // set the field values to the xml ids
        termTitle = findViewById(R.id.editTextTermTitle);
        startDate = findViewById(R.id.editTextStartDate);
        endDate = findViewById(R.id.editTextEndDate);
        createTerm = findViewById(R.id.createTermButton);

        dateTextField = findViewById(R.id.editTextDate2);

        // creates an instance of view model to use
        termViewModel = new ViewModelProvider.AndroidViewModelFactory(NewTerm.this
                .getApplication())
                .create(TermViewModel.class);


        // shows the date picker onClick
        dateTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewTerm.this, dateDialog,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();

            }
        });


        // gets the values from date picker onDataSet
        dateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(year, month, day);
                dateToSet = calendar.getTime();

                dateTextField.setText(dateToSet.toString());

            }
        };


        // creates the term
        createTerm.setOnClickListener(view -> {
            String title = termTitle.getText().toString();
            String start = startDate.getText().toString();
//            String end = endDate.getText().toString();



            Term term = new Term(title, start, dateToSet );

            TermViewModel.insert(term);

        });






    }


//    public void onClickCreateTerm(View view) {
//        if(!TextUtils.isEmpty(termTitle.getText().toString())) {
//            String title = termTitle.getText().toString();
//            String start = startDate.getText().toString();
//            String end = endDate.getText().toString();
//
//            Term term = new Term(title, start, end);
//
//            TermViewModel.insert(term);
//
//        } else {
//            Toast.makeText(this, R.string.emptyInput, Toast.LENGTH_SHORT).show();
//        }
//
//    }


}