package com.VFeskin.collegecoursetracker.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.VFeskin.collegecoursetracker.DAO.AssessmentDAO;
import com.VFeskin.collegecoursetracker.DAO.CourseDAO;
import com.VFeskin.collegecoursetracker.DAO.TermDAO;
import com.VFeskin.collegecoursetracker.Entitys.Assessment;
import com.VFeskin.collegecoursetracker.Entitys.Course;
import com.VFeskin.collegecoursetracker.Entitys.Term;

@Database(entities = { Term.class, Course.class, Assessment.class }, version = 1)
public abstract class CourseTrackerDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static CourseTrackerDatabase DATABASE;

    // Singleton
    public static CourseTrackerDatabase getInstance(Context context) {
        if(DATABASE == null) {
            DATABASE = Room
                    .databaseBuilder(context, CourseTrackerDatabase.class, "course_tracker_db")
                    .build();
        }
        return DATABASE;
    }

}
