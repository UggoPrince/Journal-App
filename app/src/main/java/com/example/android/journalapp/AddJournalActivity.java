/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import android.widget.Toast;

import com.example.android.journalapp.utilities.Journal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * This class adds a journal
 */

public class AddJournalActivity extends AppCompatActivity {

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

        setContentView(R.layout.activity_add_journal);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mTitle = (EditText) findViewById(R.id.editTextJournalTitle);
        mBody = (EditText) findViewById(R.id.editTextJournalBody);
        mFab = (FloatingActionButton) findViewById(R.id.fab_save);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Toast.makeText(AddJournalActivity.this, "Journal Saved!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddJournalActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                String title = mTitle.getText().toString();
                String body = mBody.getText().toString();
                //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                //String dTime = formatter.format(date);

                if (TextUtils.isEmpty(title)){
                    Toast.makeText(AddJournalActivity.this, "Enter a title!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(body)){
                    Toast.makeText(AddJournalActivity.this, "Enter a Journal", Toast.LENGTH_LONG).show();
                    return;
                }

                Journal journal = new Journal(title, body, date);
                databaseReference.child("user").child(currentUser.getUid()).push().setValue(journal);
            }
        });
    }
}
