package com.example.android.journalapp.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.journalapp.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Journal> journal;
    protected Context context;

    public RecyclerViewAdapter(Context cont, List<Journal> jonal){
        this.journal = jonal;
        this.context = cont;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_list, parent, false);
        viewHolder = new RecyclerViewHolder(layoutView, journal);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.journalTitle.setText(journal.get(position).getTitle());
        holder.dateText.setText(journal.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return this.journal.size();
    }


}