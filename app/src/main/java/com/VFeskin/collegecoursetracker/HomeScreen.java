package com.VFeskin.collegecoursetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.VFeskin.collegecoursetracker.Controller.TermList;


public class HomeScreen extends AppCompatActivity {
    // XML attribute
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        startButton = findViewById(R.id.getStartedButton);
        startButton.setOnClickListener(view -> {
           openApplication();
        });
    }

    public void openApplication() {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

}