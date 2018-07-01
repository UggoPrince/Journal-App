package com.example.android.journalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.journalapp.utilities.Journal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class JournalUpdate extends AppCompatActivity {

    private EditText mTitle;
    private EditText mBody;
    private FloatingActionButton mFab;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        setContentView(R.layout.activity_journal_update);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mTitle = (EditText) findViewById(R.id.editTextUpdateTitle);
        mBody = (EditText) findViewById(R.id.editTextUpdateBody);
        mFab = (FloatingActionButton) findViewById(R.id.fab_update);

        Intent sentIntent = getIntent();

        if (sentIntent.hasExtra("title")){
            String sentDate = sentIntent.getStringExtra("date");
            String sentTitle = sentIntent.getStringExtra("title");
            String sentBody = sentIntent.getStringExtra("body");

            mTitle.setText(sentTitle);
            mBody.setText(sentBody);
        }

        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString();
                String body = mBody.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(JournalUpdate.this, "Enter a title!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(body)) {
                    Toast.makeText(JournalUpdate.this, "Enter a Journal", Toast.LENGTH_LONG).show();
                    return;
                }
                Date date = new Date();
                String dTime = date.toString();
                Journal journal = new Journal(title, body, date);
            }
        });
    }
}
