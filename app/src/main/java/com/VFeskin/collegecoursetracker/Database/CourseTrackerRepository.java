package com.VFeskin.collegecoursetracker.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.VFeskin.collegecoursetracker.DAO.AssessmentDAO;
import com.VFeskin.collegecoursetracker.DAO.CourseDAO;
import com.VFeskin.collegecoursetracker.DAO.NotesDAO;
import com.VFeskin.collegecoursetracker.DAO.TermDAO;
import com.VFeskin.collegecoursetracker.DAO.UserDao;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.Note;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.Model.User;

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
    private UserDao userDao;

    // Observed LiveData will notify the observer when the data has changed.
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Assessment>> allAssessments;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<User>> allUsers;


    public CourseTrackerRepository(Application application) {
        CourseTrackerDatabase db = CourseTrackerDatabase.getInstance(application);
        // create the implicit dao subclass implementations
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
        notesDAO = db.notesDAO();
        userDao = db.userDao();

        // get all
        allTerms = termDAO.getAllTerms();
        allCourses = courseDAO.getAllCourses();
        allAssessments = assessmentDAO.getAllAssessments();
        allNotes = notesDAO.getAllNotes();
        allUsers = userDao.getAllUsers();
    }

    ////// Must call the following on a non-UI thread or it will throw an exception ////////

    /// TERM CRUD///
    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public LiveData<Term> getByTermPK(int id) {
        return termDAO.getByTermPK(id);
    }

    public LiveData<List<Term>> searchForTerms(String query) {
        return termDAO.searchForTerms(query);
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

    public LiveData<List<Course>> searchForCourses(String query) {
        return courseDAO.searchForCourses(query);
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

    public LiveData<List<Assessment>> searchForAssessments(String query) {
        return assessmentDAO.searchForAssessments(query);
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

    /// USER ///
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<User> getUser(String user, String password) {
        return userDao.getUser(user,password);
    }

    public LiveData<User> getUserName(String user) {
        return userDao.getUserName(user);
    }

    public LiveData<User> getUserPassword(String password) {
        return userDao.getUserPassword(password);
    }

    public void insertUser(User user) {
        CourseTrackerDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }


}
