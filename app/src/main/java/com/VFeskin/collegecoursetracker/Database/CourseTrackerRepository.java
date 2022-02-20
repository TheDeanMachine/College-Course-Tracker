package com.VFeskin.collegecoursetracker.Database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.VFeskin.collegecoursetracker.DAO.TermDAO;
import com.VFeskin.collegecoursetracker.Entitys.Term;
import java.util.List;


public class CourseTrackerRepository {

    private TermDAO termDAO;
    private LiveData<List<Term>> allTerms;

    CourseTrackerRepository(Application application) {
        CourseTrackerDatabase db = CourseTrackerDatabase.getInstance(application);
        termDAO = db.termDAO();
        allTerms = termDAO.getAllTerms();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    // You must call this on a non-UI thread or app will throw an exception.
    // Room ensures that you're not doing any long running operations on the main thread, blocking the UI.
    void insertTerm(Term term) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            termDAO.insert(term);
        });
    }


}
