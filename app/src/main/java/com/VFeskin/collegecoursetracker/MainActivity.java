package com.VFeskin.collegecoursetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.VFeskin.collegecoursetracker.Model.TermViewModel;

public class MainActivity extends AppCompatActivity {

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        termViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(TermViewModel.class);

        termViewModel.getAllTerms().observe(this, term -> {
            Log.d("TAG", "onCreate " + term.get(0).getTitle());
        });


    }
}