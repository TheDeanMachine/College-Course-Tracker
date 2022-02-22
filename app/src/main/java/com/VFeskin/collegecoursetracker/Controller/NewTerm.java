package com.VFeskin.collegecoursetracker.Controller;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.Model.TermViewModel;
import com.VFeskin.collegecoursetracker.R;

public class NewTerm extends AppCompatActivity {

    private EditText termTitle;
    private EditText startDate;
    private EditText endDate;
    private Button createTerm;

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);
        // set the field values to the xml id
        termTitle = findViewById(R.id.editTextTermTitle);
        startDate = findViewById(R.id.editTextStartDate);
        endDate = findViewById(R.id.editTextEndDate);
        createTerm = findViewById(R.id.createTermButton);

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(NewTerm.this
                .getApplication())
                .create(TermViewModel.class);



        createTerm.setOnClickListener(view -> {

            String title = termTitle.getText().toString();
            String start = startDate.getText().toString();
            String end = endDate.getText().toString();

            Term term = new Term(title, start, end);

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