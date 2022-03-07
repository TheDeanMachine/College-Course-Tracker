package com.VFeskin.collegecoursetracker.Database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.VFeskin.collegecoursetracker.DAO.AssessmentDAO;
import com.VFeskin.collegecoursetracker.DAO.CourseDAO;
import com.VFeskin.collegecoursetracker.DAO.TermDAO;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Room Database.
 * Room executes all queries on a separate thread and ensures that
 * you're not doing any long running operations on the main thread, blocking the UI.
 */
@Database(entities = { Term.class, Course.class, Assessment.class }, version = 5, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class CourseTrackerDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile CourseTrackerDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Singleton
    public static CourseTrackerDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (CourseTrackerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CourseTrackerDatabase.class, "course_tracker_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
