package com.VFeskin.collegecoursetracker.Model;

import android.app.Application;
import com.VFeskin.collegecoursetracker.Database.CourseTrackerRepository;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

/**
 * This class is the ViewModel for Term.
 * The ViewModel's role is to provide data to the UI and survive configuration changes.
 * A ViewModel acts as a communication center between the Repository and the UI.
 */
public class TermViewModel extends AndroidViewModel {

    public static CourseTrackerRepository repository;
    public final LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseTrackerRepository(application);
        allTerms = repository.getAllTerms();
    }

    // CRUD
    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public LiveData<Term> getByTermPK(int id) {
        return repository.getByTermPK(id);
    }

    public LiveData<List<Term>> searchForTerms(String query) {
        return repository.searchForTerms(query);
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
