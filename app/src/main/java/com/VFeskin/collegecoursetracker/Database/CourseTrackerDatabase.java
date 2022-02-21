package com.VFeskin.collegecoursetracker.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.VFeskin.collegecoursetracker.DAO.AssessmentDAO;
import com.VFeskin.collegecoursetracker.DAO.CourseDAO;
import com.VFeskin.collegecoursetracker.DAO.TermDAO;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.Term;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
@Database(entities = { Term.class, Course.class, Assessment.class }, version = 1, exportSchema = false)
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
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
