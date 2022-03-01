package com.VFeskin.collegecoursetracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.R;
import java.util.List;


/**
 * RecyclerView adapter class for Term objects.
 * RecyclerView library dynamically creates the elements when they're needed.
 */
public class TermViewAdapter extends RecyclerView.Adapter<TermViewAdapter.ViewHolder> {

    private List<Term> termList;

    public TermViewAdapter(List<Term> termList) {
        this.termList = termList;
    }

    /*
     * RecyclerView calls this method whenever it needs to create a new ViewHolder.
     * The method creates and initializes the ViewHolder and its associated View,
     * but does not fill in the view's contents, the ViewHolder has not yet been bound to specific data.
     */
    @NonNull
    @Override
    public TermViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // term card
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_card, parent, false);

        return new ViewHolder(view);
    }

    /*
     * RecyclerView calls this method to associate a ViewHolder with data.
     * The method fetches the appropriate data and uses the data to fill in the view holder's layout.
     */
    @Override
    public void onBindViewHolder(@NonNull TermViewAdapter.ViewHolder holder, int position) {
        // get term at position in the list
        Term term = termList.get(position);
        // set the card view with its data
        holder.title.setText(term.getTitle());
        holder.start.setText(term.getStartDate().toString());
        holder.end.setText(term.getEndDate().toString());

    }

    /*
     * RecyclerView calls this method to get the size of the data set,
     * it uses this to determine when there are no more items that can be displayed.
     */
    @Override
    public int getItemCount() {
        return termList.size();
    }

    // Provide a reference to the type of view items
    public class ViewHolder extends RecyclerView.ViewHolder {
        // XML attributes
        public TextView title;
        public TextView start;
        public TextView end;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTermTitle);
            start = itemView.findViewById(R.id.textViewTermStartDate);
            end = itemView.findViewById(R.id.textViewTermEndDate);
        }
    }
}
