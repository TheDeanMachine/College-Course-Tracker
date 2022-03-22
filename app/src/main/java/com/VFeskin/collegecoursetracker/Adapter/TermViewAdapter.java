package com.VFeskin.collegecoursetracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Model.Term;
import com.VFeskin.collegecoursetracker.R;
import java.text.DateFormat;
import java.util.List;


/**
 * RecyclerView adapter class for Term objects.
 * RecyclerView library dynamically creates the elements when they're needed.
 */
public class TermViewAdapter extends RecyclerView.Adapter<TermViewAdapter.ViewHolder> {

    private List<Term> termList;
    private OnTermClickListener onTermClickListener;

    public TermViewAdapter(List<Term> termList, OnTermClickListener onTermClickListener) {
        this.termList = termList;
        this.onTermClickListener = onTermClickListener;
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

        // return a view with an attached listener
        return new ViewHolder(view, onTermClickListener);
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
        holder.start.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(term.getStartDate()));
        holder.end.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(term.getEndDate()));
    }

    /*
     * RecyclerView calls this method to get the size of the data set,
     * it uses this to determine when there are no more items that can be displayed.
     */
    @Override
    public int getItemCount() {
        return termList.size();
    }

    // Provides a reference to the type of view items
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // XML attributes
        public TextView title;
        public TextView start;
        public TextView end;

        // listener reference
        OnTermClickListener onTermClickListener;

        public ViewHolder(@NonNull View itemView, OnTermClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTermTitle);
            start = itemView.findViewById(R.id.textViewTermStartDate);
            end = itemView.findViewById(R.id.textViewTermEndDate);

            // associate card view with a click listener
            onTermClickListener = listener;
            itemView.setOnClickListener(this);
        }

        // get position of the card in recycle view
        @Override
        public void onClick(View view) {
            onTermClickListener.onTermClick(getAdapterPosition());
        }
    }

    public interface OnTermClickListener {
        void onTermClick(int position);
    }

}
