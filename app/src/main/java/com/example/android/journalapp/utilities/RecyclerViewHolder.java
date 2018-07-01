package com.example.android.journalapp.utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.journalapp.JournalDetails;
import com.example.android.journalapp.JournalUpdate;
import com.example.android.journalapp.R;

import java.util.Date;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerViewHolder.class.getSimpleName();
    public ImageView editIcon;
    public TextView journalTitle;
    public TextView dateText;
    private LinearLayout mLayout;
    private List<Journal> journalObject;

    public RecyclerViewHolder(final View itemView, List<Journal> journal) {
        super(itemView);
        this.journalObject = journal;
        mLayout = (LinearLayout) itemView.findViewById(R.id.journal_details_ll);
        journalTitle = (TextView) itemView.findViewById(R.id.title_tv);
        dateText = (TextView) itemView.findViewById(R.id.date_tv);
        editIcon = (ImageView) itemView.findViewById(R.id.edit_button);


        mLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context detailClass = itemView.getContext();
                Class joDetails = JournalDetails.class;
                String tt = journalObject.get(getAdapterPosition()).getTitle();
                String bb = journalObject.get(getAdapterPosition()).getBody();
                Date dd = journalObject.get(getAdapterPosition()).getDate();
                String da = dd.toString();

                Intent intent = new Intent(detailClass, joDetails);
                intent.putExtra("date", da);
                intent.putExtra("title", tt);
                intent.putExtra("body", bb);

                itemView.getContext().startActivity(intent);
            }
        });

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context detailClass = itemView.getContext();
                Class joDetails = JournalUpdate.class;
                String tt = journalObject.get(getAdapterPosition()).getTitle();
                String bb = journalObject.get(getAdapterPosition()).getBody();
                Date dd = journalObject.get(getAdapterPosition()).getDate();
                String da = dd.toString();

                Intent intent = new Intent(detailClass, joDetails);
                intent.putExtra("date", da);
                intent.putExtra("title", tt);
                intent.putExtra("body", bb);

                itemView.getContext().startActivity(intent);
            }
        });
    }
}

