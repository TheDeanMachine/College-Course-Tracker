package com.VFeskin.collegecoursetracker.Database;

import android.app.Application;

import androidx.appcompat.widget.AlertDialogLayout;
import androidx.lifecycle.LiveData;

import com.VFeskin.collegecoursetracker.DAO.AssessmentDAO;
import com.VFeskin.collegecoursetracker.DAO.CourseDAO;
import com.VFeskin.collegecoursetracker.DAO.TermDAO;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.Term;
import java.util.List;

/**
 * Repository class for Room database.
 * The Repository manages queries and allows you to use multiple sources.
 */
public class CourseTrackerRepository {

    // DAO references
    private TermDAO termDAO;
    private CourseDAO courseDAO;
    private AssessmentDAO assessmentDAO;

    // Observed LiveData will notify the observer when the data has changed.
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Assessment>> allAssessments;


    public CourseTrackerRepository(Application application) {
        CourseTrackerDatabase db = CourseTrackerDatabase.getInstance(application);
        // create the implicit dao subclass implementations
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();

        // get all
        allTerms = termDAO.getAllTerms();
        allCourses = courseDAO.getAllCourses();
        allAssessments = assessmentDAO.getAllAssessments();
    }

    ////// Must call the following on a non-UI thread or it will throw an exception ////////

    /// TERM CRUD///
    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

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


    /// COURSE CRUD///
    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public void insertCourse(Course course) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            courseDAO.insert(course);
        });
    }

    public void updateCourse(Course course) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            courseDAO.update(course);
        });
    }

    public void deleteCourse(Course course) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            courseDAO.delete(course);
        });
    }


    /// ASSESSMENT CRUD ///
    public LiveData<List<Assessment>> getAllAssessment() {
        return allAssessments;
    }

    public void insertAssessments(Assessment assessment) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            assessmentDAO.insert(assessment);
        });
    }

    public void updateAssessments(Assessment assessment) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            assessmentDAO.update(assessment);
        });
    }

    public void deleteAssessments(Assessment assessment) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            assessmentDAO.delete(assessment);
        });
    }

}
