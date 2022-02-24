package com.VFeskin.collegecoursetracker.Model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.VFeskin.collegecoursetracker.Database.CourseTrackerRepository;
import java.util.List;

/**
 * This class is the ViewModel for Course.
 * The ViewModel's role is to provide data to the UI and survive configuration changes.
 * A ViewModel acts as a communication center between the Repository and the UI.
 */
public class CourseViewModel extends AndroidViewModel {

    public static CourseTrackerRepository repository;
    public final LiveData<List<Course>> allCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseTrackerRepository(application);
        allCourses = repository.getAllCourses();
    }

    // CRUD
    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public static void insert(Course course) {
        repository.insertCourse(course);
    }

    public static void update(Course course) {
        repository.updateCourse(course);
    }

    public static void delete(Course course) {
        repository.deleteCourse(course);
    }


}
