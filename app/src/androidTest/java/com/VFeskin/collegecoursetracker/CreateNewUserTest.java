package com.VFeskin.collegecoursetracker;

import static org.junit.Assert.*;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.VFeskin.collegecoursetracker.DAO.UserDao;
import com.VFeskin.collegecoursetracker.Database.CourseTrackerDatabase;
import com.VFeskin.collegecoursetracker.Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreateNewUserTest {

    private UserDao userDao;
    private CourseTrackerDatabase db;


    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CourseTrackerDatabase.class).build();
        userDao = db.userDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        User user = new User("test", "test");
        userDao.insert(user);
        User byName = userDao.getUserNameTest("test");
        assertEquals(byName.getUserName(), user.getUserName());
    }
}