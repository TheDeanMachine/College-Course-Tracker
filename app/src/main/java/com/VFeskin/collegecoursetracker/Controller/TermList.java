package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Adapter.TermViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.Model.TermViewModel;
import com.VFeskin.collegecoursetracker.R;
import android.os.Bundle;
import java.util.List;

public class TermList extends AppCompatActivity {

    // data
    private LiveData<List<Term>> termList;

    // recycle view
    private RecyclerView recyclerView;
    private TermViewAdapter termViewAdapter;

    // term view
    TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        // configure up recycle view
        recyclerView = findViewById(R.id.recycler_view_term);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        termViewModel = new ViewModelProvider.AndroidViewModelFactory(TermList.this
                .getApplication())
                .create(TermViewModel.class);

        // observer
        termViewModel.getAllTerms().observe(this, terms -> {
            // set recycle view with terms
            termViewAdapter = new TermViewAdapter(terms);
            recyclerView.setAdapter(termViewAdapter);
        });







    }
}