package com.VFeskin.collegecoursetracker.Model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.VFeskin.collegecoursetracker.Database.CourseTrackerRepository;

import java.util.List;

/**
 * This class is the ViewModel for User.
 * The ViewModel's role is to provide data to the UI and survive configuration changes.
 * A ViewModel acts as a communication center between the Repository and the UI.
 */
public class UserViewModel extends AndroidViewModel {

    public static CourseTrackerRepository repository;
    public final LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseTrackerRepository(application);
        allUsers = repository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<User> getUser(String user, String password) {
        return repository.getUser(user, password);
    }

    public static void insert(User user) {
        repository.insertUser(user);
    }

}
