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


public class TermViewAdapter extends RecyclerView.Adapter<TermViewAdapter.ViewHolder> {

    private LiveData<List<Term>> termList;



    public TermViewAdapter(LiveData<List<Term>> termList) {
        this.termList = termList;
    }


    @NonNull
    @Override
    public TermViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // term card view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewAdapter.ViewHolder holder, int position) {
        Term term = termList.getValue().get(position);
        holder.title.setText(term.getTitle());
        holder.start.setText(term.getStartDate().toString());
        holder.end.setText(term.getEndDate().toString());

    }

    @Override
    public int getItemCount() {
        return termList.getValue().size(); //Attempt to invoke interface method 'int java.util.List.size()' on a null object reference
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

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
