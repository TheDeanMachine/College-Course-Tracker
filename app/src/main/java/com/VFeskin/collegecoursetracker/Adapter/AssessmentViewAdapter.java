package com.VFeskin.collegecoursetracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Model.Assessment;
import com.VFeskin.collegecoursetracker.R;

import java.text.DateFormat;
import java.util.List;

/**
 * RecyclerView adapter class for Assessment objects.
 * RecyclerView library dynamically creates the elements when they're needed.
 */
public class AssessmentViewAdapter extends RecyclerView.Adapter<AssessmentViewAdapter.ViewHolder> {

    private List<Assessment> assessmentList;
    private OnAssessmentClickListener onAssessmentClickListener;

    public AssessmentViewAdapter(List<Assessment> assessmentList, OnAssessmentClickListener onAssessmentClickListener) {
        this.assessmentList = assessmentList;
        this.onAssessmentClickListener = onAssessmentClickListener;
    }

    /*
     * RecyclerView calls this method whenever it needs to create a new ViewHolder.
     * The method creates and initializes the ViewHolder and its associated View,
     * but does not fill in the view's contents, the ViewHolder has not yet been bound to specific data.
     */
    @NonNull
    @Override
    public AssessmentViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // assessment card
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_card, parent, false);

        // return a view with an attached listener
        return new ViewHolder(view, onAssessmentClickListener);
    }

    /*
     * RecyclerView calls this method to associate a ViewHolder with data.
     * The method fetches the appropriate data and uses the data to fill in the view holder's layout.
     */
    @Override
    public void onBindViewHolder(@NonNull AssessmentViewAdapter.ViewHolder holder, int position) {
        // get assessment at position in list
        Assessment assessment = assessmentList.get(position);

        // set the card view with its data
        holder.title.setText(assessment.getTitle());
        holder.start.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
                .format(assessment.getStartDateTime()));
    }

    /*
     * RecyclerView calls this method to get the size of the data set,
     * it uses this to determine when there are no more items that can be displayed.
     */
    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    // Provides a reference to the type of view items
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // XML attributes
        public TextView title;
        public TextView start;

        // listener reference
        OnAssessmentClickListener onAssessmentClickListener;

        public ViewHolder(@NonNull View itemView, OnAssessmentClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewAssessmentTitle);
            start = itemView.findViewById(R.id.textViewAssessmentStartDate);

            // associate card view with a click listener
            onAssessmentClickListener = listener;
            itemView.setOnClickListener(this);
        }

        // get position of the card in recycle view
        @Override
        public void onClick(View view) {
            onAssessmentClickListener.onAssessmentClick(getAdapterPosition());
        }
    }

    public interface OnAssessmentClickListener {
        void onAssessmentClick(int position);
    }
}
