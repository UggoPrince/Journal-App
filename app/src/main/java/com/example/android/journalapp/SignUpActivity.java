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
 *This class builds the sign up view layout
 */

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText mEmailEntry;
    private EditText mPasswordEntry;
    private Button mSignUpButton;
    private Button mForgotPasswordButton;
    private Button mSignInLinkButton;
    private ProgressBar mProBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_sign_up);

        mEmailEntry = (EditText) findViewById(R.id.sign_up_email);
        mPasswordEntry = (EditText) findViewById(R.id.sign_up_password);
        mSignUpButton = (Button) findViewById(R.id.btn_sign_up);
        mForgotPasswordButton = (Button) findViewById(R.id.btn_reset_password2);
        mSignInLinkButton = (Button) findViewById(R.id.btn_link_for_sign_in);
        mProBar = (ProgressBar) findViewById(R.id.progressBar_sign_up);

        mSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEntry.getText().toString().trim();
                String password = mPasswordEntry.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Enter Email Address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProBar.setVisibility(View.VISIBLE);

                //create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "Registrastion Successful: " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                mProBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()){
                                    Toast.makeText(SignUpActivity.this, "Authentication Failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });

        mForgotPasswordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
            }
        });

        mSignInLinkButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = SignUpActivity.this;
                Class signInActivity = SignInActivity.class;
                Intent intent = new Intent(context, signInActivity);
                startActivity(intent);
                finish();
            }
        });
    }
}
