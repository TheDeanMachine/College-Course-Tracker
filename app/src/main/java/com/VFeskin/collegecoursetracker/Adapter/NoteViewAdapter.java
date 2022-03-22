package com.VFeskin.collegecoursetracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.VFeskin.collegecoursetracker.Model.Note;
import com.VFeskin.collegecoursetracker.R;
import java.util.List;

/**
 * RecyclerView adapter class for Note objects.
 * RecyclerView library dynamically creates the elements when they're needed.
 */
public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.ViewHolder> {

    private List<Note> noteList;
    private OnNoteClickListener onNoteClickListener;

    public NoteViewAdapter(List<Note> noteList, OnNoteClickListener onNoteClickListener) {
        this.noteList = noteList;
        this.onNoteClickListener = onNoteClickListener;
    }

    /*
     * RecyclerView calls this method whenever it needs to create a new ViewHolder.
     * The method creates and initializes the ViewHolder and its associated View,
     * but does not fill in the view's contents, the ViewHolder has not yet been bound to specific data.
     */
    @NonNull
    @Override
    public NoteViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_card, parent, false);

        // return a view with an attached listener
        return new ViewHolder(view, onNoteClickListener);
    }

    /*
     * RecyclerView calls this method to associate a ViewHolder with data.
     * The method fetches the appropriate data and uses the data to fill in the view holder's layout.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.note.setText(note.getNote());
    }

    /*
     * RecyclerView calls this method to get the size of the data set,
     * it uses this to determine when there are no more items that can be displayed.
     */
    @Override
    public int getItemCount() {
        return noteList.size();
    }

    // Provides a reference to the type of view items
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // XML attributes
        public TextView note;

        // listener reference
        OnNoteClickListener onNoteClickListener;

        public ViewHolder(@NonNull View itemView, OnNoteClickListener listener) {
            super(itemView);
            note = itemView.findViewById(R.id.textViewNoteCard);

            // associate card view with a click listener
            onNoteClickListener = listener;
            itemView.setOnClickListener(this);
        }

        // get position of the card in recycle view
        @Override
        public void onClick(View view) {
            onNoteClickListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteClickListener {
        void onNoteClick(int position);
    }

}
