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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/*
 *This class builds the sign up view layout
 */

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmailEntry;
    private EditText mPasswordEntry;
    private Button mSignUpButton;
    private Button mForgotPasswordButton;
    private Button mSignInLinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmailEntry = (EditText) findViewById(R.id.sign_up_email);
        mPasswordEntry = (EditText) findViewById(R.id.sign_up_password);
        mSignUpButton = (Button) findViewById(R.id.btn_sign_up);
        mForgotPasswordButton = (Button) findViewById(R.id.btn_reset_password2);
        mSignInLinkButton = (Button) findViewById(R.id.btn_link_for_sign_in);

        mSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = SignUpActivity.this;
                Class journalActivity = MainActivity.class;
                Intent intent = new Intent(context, journalActivity);
                startActivity(intent);
                finish();
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
