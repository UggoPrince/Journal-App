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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.journalapp.utilities.Journal;
import com.example.android.journalapp.utilities.RecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * This class Builds the Journal App Layout and populates with journals
 */

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private DatabaseReference databaseReference;
    private DatabaseReference users;
    private List<Journal> journals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser == null) {
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        }



        setContentView(R.layout.activity_main);

        journals = new ArrayList<Journal>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) findViewById(R.id.journal_rv);
        FloatingActionButton fabButton = findViewById(R.id.fab_add_journal);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        users = databaseReference.child("users").child(currentUser.getUid());


        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadAllJournals(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addJournalIntent = new Intent(MainActivity.this, AddJournalActivity.class);
                startActivity(addJournalIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.journal_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_sign_out:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
                break;
        }
        return true;
    }

    private void loadAllJournals(DataSnapshot dataSnapshot){
        //if (dataSnapshot != null){
            for (DataSnapshot oneSnapshot : dataSnapshot.getChildren()){
                Journal jonal = oneSnapshot.getValue(Journal.class);
                String title = jonal.getTitle();
                String body = jonal.getBody();
                Date date = jonal.getDate();
                journals.add(new Journal(title, body, date));
                recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, journals);
                recyclerView.setAdapter(recyclerViewAdapter);
            }
    }

} // my firebase project ID = journal-app-e48cd
