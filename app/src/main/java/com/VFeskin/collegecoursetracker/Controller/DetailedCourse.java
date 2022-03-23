package com.VFeskin.collegecoursetracker.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Adapter.AssessmentViewAdapter;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.Model.AssessmentViewModel;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.Model.CourseViewModel;
import com.VFeskin.collegecoursetracker.R;
import com.VFeskin.collegecoursetracker.Utility.DateConverter;
import com.VFeskin.collegecoursetracker.Utility.DateReceiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * This class displays a selected course in detail.
 * The details include a RecyclerView with assessments assigned to this course.
 */
public class DetailedCourse extends AppCompatActivity implements AssessmentViewAdapter.OnAssessmentClickListener {

    // XML attributes
    private TextView title;
    private TextView status;
    private TextView start;
    private TextView end;
    private TextView startTimeTxt;
    private TextView endTimeTxt;
    private TextView roomNum;
    private TextView name;
    private TextView phone;
    private TextView email;

    // data
    private int PK;
    private int FK;
    private Date startDate;
    private Date endDate;
    private Long startTime;
    private Long endTime;
    private LiveData<List<Assessment>> assessmentList;
    private LiveData<List<Assessment>> assessmentsByCourseId;
    private LiveData<Course> courseById;

    // recycle view
    private RecyclerView recyclerView;
    private AssessmentViewAdapter assessmentViewAdapter;

    // view model
    private CourseViewModel courseViewModel;
    private AssessmentViewModel assessmentViewModel;

    // add button
    private FloatingActionButton fab;
//    private FloatingActionButton fabAlarm;
    private Button buttonAlarm;
    private Button viewNotesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course);
        fab = findViewById(R.id.detail_add_new_assessment);
//        fabAlarm = findViewById(R.id.add_course_alarm);
        buttonAlarm = findViewById(R.id.add_course_alarm);
        viewNotesButton = findViewById(R.id.ViewNotesButton);
        title = findViewById(R.id.textViewDetailCourseTitle);
        status = findViewById(R.id.textViewDetailCourseStatus);
        start = findViewById(R.id.textViewDetailCourseStartDate);
        end = findViewById(R.id.textViewDetailCourseEndDate);
        startTimeTxt = findViewById(R.id.textViewDetailCourseTime);
        endTimeTxt = findViewById(R.id.textViewDetailCourseEndTime);
        roomNum = findViewById(R.id.textViewDetailCourseRoom);
        name = findViewById(R.id.textViewDetailCourseInstructorName);
        phone = findViewById(R.id.textViewDetailCourseInstructorPhone);
        email = findViewById(R.id.textViewDetailCourseInstructorEmail);

        PK = getIntent().getIntExtra("ID", 0);

        // configure recycle view
        recyclerView = findViewById(R.id.detail_course_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates an instance of view model to use
        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedCourse.this
                .getApplication())
                .create(AssessmentViewModel.class);

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(DetailedCourse.this
                .getApplication())
                .create(CourseViewModel.class);

        // assessment list observers
        assessmentsByCourseId = assessmentViewModel.getByCourseId(PK);
        assessmentsByCourseId.observe(this, assessments -> {
            // set recycle view with assessments
            assessmentViewAdapter = new AssessmentViewAdapter(assessments, this);
            recyclerView.setAdapter(assessmentViewAdapter);
        });

        // selected course observer
        courseById = courseViewModel.getByCoursePK(PK);
        courseById.observe(this, course -> {
            title.setText(course.getTitle());
            status.setText(course.getCourseStatus());
            startDate = course.getStartDateTime();
            endDate = course.getEndDateTime();
            start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(course.getStartDateTime()));
            end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(course.getEndDateTime()));
            startTime = startDate.getTime();
            startTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime));
            endTime = endDate.getTime();
            endTimeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(endTime));
            roomNum.setText(course.getRoomNumber());
            name.setText(course.getInstructorName());
            phone.setText(course.getInstructorPhone());
            email.setText(course.getInstructorEmail());
            FK = course.getTermId();
        });

        // add new assessment
        fab.setOnClickListener(view -> {
            openNewAssessment();
        });

        // add alarm
//        fabAlarm.setOnClickListener(this::addCourseAlarm);
        buttonAlarm.setOnClickListener(this::addCourseAlarm);
        viewNotesButton.setOnClickListener(this::viewNotes);
    }

    private void viewNotes(View view) {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra("ID", PK);
        startActivity(intent);
    }


    private void addCourseAlarm(View view) {
        Long trigger = startDate.getTime();
        String title = Objects.requireNonNull(courseById.getValue()).getTitle();

        Intent intent = new Intent(this, DateReceiver.class);
        intent.putExtra("KEY", "Course " + title + " starts today" );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, DateReceiver.numAlert++ ,intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);

        Snackbar.make(view, "Alarm for " + title + " set", Snackbar.LENGTH_LONG).show();
    }

    private void openNewAssessment() {
        Intent intent = new Intent(this, NewAssessment.class);
        intent.putExtra("ID", PK);
        startActivity(intent);
    }

    @Override
    public void onAssessmentClick(int position) {
        Intent intent = new Intent(this, DetailedAssessment.class);

        // pass data to the detail view
        Assessment assessment = Objects.requireNonNull(assessmentsByCourseId.getValue()).get(position);
        intent.putExtra("ID", assessment.getId());
        intent.putExtra("TITLE", assessment.getTitle());
        intent.putExtra("TEST", assessment.getAssessmentType());
        intent.putExtra("START", DateConverter.ToTimestamp(assessment.getStartDateTime()));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // set share button if optional note is found
//        if (Objects.requireNonNull(courseById.getValue()).getNote() != null) {
//            MenuItem shareItem = menu.findItem(R.id.share);
//            shareItem.setVisible(true);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which app bar item was chosen
        switch (item.getItemId()) {
//            case R.id.share:
//                shareItem();
//                return true;
            case R.id.edit:
                editItem();
                return true;
            case R.id.delete:
                deleteItem();
                return true;
            case R.id.AllTerms:
                viewAllTerms();
                return true;
            case R.id.AllCourses:
                viewAllCourses();
                return true;
            case R.id.AllAssessments:
                viewAllAssessments();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void shareItem() {
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, Objects.requireNonNull(courseById.getValue()).getNote());
//        sendIntent.setType("text/plain");
//
//        Intent shareIntent = Intent.createChooser(sendIntent, null);
//        startActivity(shareIntent);
//    }

    private void editItem() {
        Intent intent = new Intent(this, EditCourse.class);
        intent.putExtra("ID", PK);
        intent.putExtra("TITLE", title.getText());
        intent.putExtra("STATUS", status.getText());
        intent.putExtra("START", DateConverter.ToTimestamp(startDate));
        intent.putExtra("END", DateConverter.ToTimestamp(endDate));
        intent.putExtra("ROOM", roomNum.getText());
        intent.putExtra("NAME", name.getText());
        intent.putExtra("PHONE", phone.getText());
        intent.putExtra("EMAIL", email.getText());
        intent.putExtra("FK", FK);
//
//        if (Objects.requireNonNull(courseById.getValue()).getNote() != null) {
//            intent.putExtra("NOTE", note.getText());
//        }
        startActivity(intent);
    }

    private void deleteItem() {
        if (courseById.hasObservers()) {
            courseById.removeObservers(DetailedCourse.this);
            CourseViewModel.delete(new Course(PK, title.toString(), startDate, endDate,
                    status.toString(), name.toString(), phone.toString(), email.toString(), null, FK));
            finish();
        }
    }

    public void viewAllTerms() {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
        finish();
    }

    public void viewAllCourses() {
        Intent intent = new Intent(this, CourseList.class);
        startActivity(intent);
        finish();
    }

    public void viewAllAssessments() {
        Intent intent = new Intent(this, AssessmentList.class);
        startActivity(intent);
        finish();
    }


}