package com.VFeskin.collegecoursetracker.Model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.VFeskin.collegecoursetracker.Database.CourseTrackerRepository;
import java.util.List;

/**
 * This class is the ViewModel for Assessment.
 * The ViewModel's role is to provide data to the UI and survive configuration changes.
 * A ViewModel acts as a communication center between the Repository and the UI.
 */
public class AssessmentViewModel extends AndroidViewModel {

    public static CourseTrackerRepository repository;
    public final LiveData<List<Assessment>> allAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseTrackerRepository(application);
        allAssessments = repository.getAllAssessment();
    }

    // CRUD
    public LiveData<List<Assessment>> getAllAssessment() {
        return allAssessments;
    }

    public LiveData<List<Assessment>> getByCourseId(int id) {
        return repository.getByCourseId(id);
    }

    public LiveData<Assessment> getByAssessmentsPK(int id) {
        return repository.getByAssessmentsPK(id);
    }

    public static void insert(Assessment assessment) {
        repository.insertAssessments(assessment);
    }

    public static void update(Assessment assessment) {
        repository.updateAssessments(assessment);
    }

    public static void delete(Assessment assessment) {
        repository.deleteAssessments(assessment);
    }


}
