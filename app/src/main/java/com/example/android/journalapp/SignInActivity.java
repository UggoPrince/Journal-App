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

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/*
 * Builds the Sign In Layout
 */

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    private EditText mEmailEntry;
    private EditText mPasswordEntry;
    private Button signInButton;
    private Button mForgotPasswordButton;
    private Button signUpLinkButton;
    private ProgressBar mProBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        //set the view
        setContentView(R.layout.activity_sign_in);

        mEmailEntry = (EditText) findViewById(R.id.sign_in_email);
        mPasswordEntry = (EditText) findViewById(R.id.sign_in_password);
        signInButton = (Button) findViewById(R.id.btn_sign_in);
        mForgotPasswordButton = (Button) findViewById(R.id.btn_reset_password1);
        signUpLinkButton = (Button) findViewById(R.id.btn_link_for_sign_up);
        mProBar = (ProgressBar) findViewById(R.id.progressBar_sign_in);

        signInButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = mEmailEntry.getText().toString();
                final String password = mPasswordEntry.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Enter Email Address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProBar.setVisibility(View.VISIBLE);

                // authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProBar.setVisibility(View.INVISIBLE);
                                if (!task.isSuccessful()){
                                    // an error occurred
                                    if (password.length() < 6) {
                                        mPasswordEntry.setError(getString(R.string.minimum_password));
                                    } else {
                                        Log.w(TAG, "Sign In : failed", task.getException());
                                        Toast.makeText(SignInActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Log.d(TAG, "Sign In : success");
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

        mForgotPasswordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
            }
        });

        signUpLinkButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = SignInActivity.this;
                Class signUpActivity = SignUpActivity.class;
                Intent intent = new Intent(context, signUpActivity);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
