package com.example.android.journalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JournalDetails extends AppCompatActivity {

    private TextView mTitle;
    private TextView mBody;
    private TextView mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_details);

        mDate = (TextView) findViewById(R.id.journal_details_date_tv);
        mTitle = (TextView) findViewById(R.id.journal_details_title_tv);
        mBody = (TextView) findViewById(R.id.journal_details_body_tv);

        Intent sentIntent = getIntent();

        if (sentIntent.hasExtra("title")){
            String sentDate = sentIntent.getStringExtra("date");
            String sentTitle = sentIntent.getStringExtra("title");
            String sentBody = sentIntent.getStringExtra("body");
            mDate.setText(sentDate);
            mTitle.setText(sentTitle);
            mBody.setText(sentBody);
        }
    }
}
