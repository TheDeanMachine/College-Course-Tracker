package com.VFeskin.collegecoursetracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Model.Course;
import com.VFeskin.collegecoursetracker.R;
import java.util.List;

/**
 * RecyclerView adapter class for Course objects.
 * RecyclerView library dynamically creates the elements when they're needed.
 */
public class CourseViewAdapter extends RecyclerView.Adapter<CourseViewAdapter.ViewHolder> {

    private List<Course> courseList;
    private OnCourseClickListener onCourseClickListener;

    public CourseViewAdapter(List<Course> courseList, OnCourseClickListener onCourseClickListener) {
        this.courseList = courseList;
        this.onCourseClickListener = onCourseClickListener;
    }

    /*
     * RecyclerView calls this method whenever it needs to create a new ViewHolder.
     * The method creates and initializes the ViewHolder and its associated View,
     * but does not fill in the view's contents, the ViewHolder has not yet been bound to specific data.
     */
    @NonNull
    @Override
    public CourseViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // course card
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_card, parent, false);

        // return a view with an attached listener
        return new ViewHolder(view, onCourseClickListener);
    }

    /*
     * RecyclerView calls this method to associate a ViewHolder with data.
     * The method fetches the appropriate data and uses the data to fill in the view holder's layout.
     */
    @Override
    public void onBindViewHolder(@NonNull CourseViewAdapter.ViewHolder holder, int position) {
        // get course at a position in the list
        Course course = courseList.get(position);
        // set the card view with its data
        holder.title.setText(course.getTitle());
        holder.start.setText(course.getStartDate().toString());
        holder.end.setText(course.getEndDate().toString());
//        holder.status.setText(course.getCourseStatus().toString());
//        holder.name.setText(course.getInstructorName());
//        holder.phone.setText(course.getInstructorPhone());
//        holder.email.setText(course.getInstructorEmail());

    }

    /*
     * RecyclerView calls this method to get the size of the data set,
     * it uses this to determine when there are no more items that can be displayed.
     */
    @Override
    public int getItemCount() {
        return courseList.size();
    }

    // Provides a reference to the type of view items
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // XML attributes
        public TextView title;
        public TextView start;
        public TextView end;
        public TextView status;
        public TextView name;
        public TextView phone;
        public TextView email;

        // listener reference
        OnCourseClickListener onCourseClickListener;

        public ViewHolder(@NonNull View itemView, OnCourseClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewCourseTitle);
            start = itemView.findViewById(R.id.textViewCourseStartDate);
            end = itemView.findViewById(R.id.textViewCourseEndDate);
//            status = itemView.findViewById(R.id.textViewCourseStatus);
//            name = itemView.findViewById(R.id.textViewCourseInstructorName);
//            phone = itemView.findViewById(R.id.textViewCourseInstructorPhone);
//            email = itemView.findViewById(R.id.textViewCourseInstructorEmail);

            // associate card view with a click listener
            onCourseClickListener = listener;
            itemView.setOnClickListener(this);
        }

        // get position of the card in recycle view
        @Override
        public void onClick(View view) {
            onCourseClickListener.onCourseClick(getAdapterPosition());
        }
    }

    public interface OnCourseClickListener {
        void onCourseClick(int position);
    }
}
