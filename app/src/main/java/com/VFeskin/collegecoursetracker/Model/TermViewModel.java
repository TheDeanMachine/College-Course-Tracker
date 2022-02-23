package com.VFeskin.collegecoursetracker.Model;

import android.app.Application;
import com.VFeskin.collegecoursetracker.Database.CourseTrackerRepository;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;


public class TermViewModel extends AndroidViewModel {

    public static CourseTrackerRepository repository;
    public final LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseTrackerRepository(application);
        allTerms = repository.getAllTerms();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public static void insert(Term term) {
        repository.insertTerm(term);
    }

    public static void update(Term term) {
        repository.updateTerm(term);
    }

    public static void delete(Term term) {
        repository.deleteTerm(term);
    }


}