package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import com.VFeskin.collegecoursetracker.R;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This class is used to view information on instructor.
 * This was previously in DetailedCourse, but layout was to crammed.
 */
public class InstructorInfo extends AppCompatActivity {

    private TextView name;
    private TextView phone;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_info);
        name = findViewById(R.id.textViewDetailCourseInstructorName);
        phone = findViewById(R.id.textViewDetailCourseInstructorPhone);
        email = findViewById(R.id.textViewDetailCourseInstructorEmail);

        // get values and set text
        Bundle extra = getIntent().getExtras();
        name.setText(extra.getString("NAME"));
        phone.setText(extra.getString("PHONE"));
        email.setText(extra.getString("EMAIL"));
    }
}