package com.VFeskin.collegecoursetracker.Database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.VFeskin.collegecoursetracker.DAO.TermDAO;
import com.VFeskin.collegecoursetracker.Model.Term;
import java.util.List;


public class CourseTrackerRepository {

    private TermDAO termDAO;
    private LiveData<List<Term>> allTerms;

    public CourseTrackerRepository(Application application) {
        CourseTrackerDatabase db = CourseTrackerDatabase.getInstance(application);
        termDAO = db.termDAO(); // very important to initialize
        allTerms = termDAO.getAllTerms();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    // You must call this on a non-UI thread or app will throw an exception.
    // Room ensures that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertTerm(Term term) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            termDAO.insert(term);
        });
    }

    public void updateTerm(Term term) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            termDAO.update(term);
        });
    }

    public void deleteTerm(Term term) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            termDAO.delete(term);
        });
    }


}
