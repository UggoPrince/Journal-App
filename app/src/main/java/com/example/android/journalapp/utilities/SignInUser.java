package com.example.android.journalapp.utilities;


import com.example.android.journalapp.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SignInUser {

    private FirebaseAuth auth;

    public void setAuth(){
        auth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth(){
        return auth;
    }
}
