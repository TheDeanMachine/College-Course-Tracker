package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import android.os.Bundle;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;

public class DetailedAssessment extends AppCompatActivity {

    // XML attributes
    public TextView title;
    public TextView testType;
    public TextView start;
    public TextView end;

    // data
    private Date startDate;
    private Date endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment);
        title = findViewById(R.id.textViewDetailAssessmentTitle);
        testType = findViewById(R.id.textViewDetailAssessmentType);
        start = findViewById(R.id.textViewDetailAssessmentStartDate);
        end = findViewById(R.id.textViewDetailAssessmentEndDate);


        Bundle extra = getIntent().getExtras();
//        id = extra.getInt("ID");
        title.setText(extra.getString("TITLE"));
        testType.setText(extra.getString("TEST"));
        startDate = DateConverter.fromTimestamp(extra.getLong("START"));
        start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate));
        endDate = DateConverter.fromTimestamp(extra.getLong("END"));
        end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(endDate));


    }
}