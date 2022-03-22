package com.VFeskin.collegecoursetracker.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.VFeskin.collegecoursetracker.DAO.AssessmentDAO;
import com.VFeskin.collegecoursetracker.DAO.CourseDAO;
import com.VFeskin.collegecoursetracker.DAO.NotesDAO;
import com.VFeskin.collegecoursetracker.DAO.TermDAO;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.Note;
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
    private NotesDAO notesDAO;

    // Observed LiveData will notify the observer when the data has changed.
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Assessment>> allAssessments;
    private LiveData<List<Note>> allNotes;


    public CourseTrackerRepository(Application application) {
        CourseTrackerDatabase db = CourseTrackerDatabase.getInstance(application);
        // create the implicit dao subclass implementations
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
        notesDAO = db.notesDAO();

        // get all
        allTerms = termDAO.getAllTerms();
        allCourses = courseDAO.getAllCourses();
        allAssessments = assessmentDAO.getAllAssessments();
        allNotes = notesDAO.getAllNotes();
    }

    ////// Must call the following on a non-UI thread or it will throw an exception ////////

    /// TERM CRUD///
    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public LiveData<Term> getByTermPK(int id) {
        return termDAO.getByTermPK(id);
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

    public LiveData<List<Course>> getByTermId(int id) {
        return courseDAO.getByTermId(id);
    }

    public LiveData<Course> getByCoursePK(int id) {
        return courseDAO.getByCoursePK(id);
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

    public LiveData<List<Assessment>> getByCourseId(int id) {
       return assessmentDAO.getByCourseId(id);
    }

    public LiveData<Assessment> getByAssessmentsPK(int id) {
        return assessmentDAO.getByAssessmentsPK(id);
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

    /// NOTES CRUD ///
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>> getNotesByCourseId(int id) {
        return notesDAO.getNotesByCourseId(id);
    }

    public LiveData<Note> getByNotesPK(int id) {
        return notesDAO.getByNotesPK(id);
    }

    public void insertNotes(Note note) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            notesDAO.insert(note);
        });
    }

    public void updateNotes(Note note) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            notesDAO.update(note);
        });
    }

    public void deleteNotes(Note note) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            notesDAO.delete(note);
        });
    }


}
