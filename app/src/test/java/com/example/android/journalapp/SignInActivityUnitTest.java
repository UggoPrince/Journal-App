package com.example.android.journalapp;

import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertThat;

/*
 * Unit tests for SignInActivity class
 */
@RunWith(MockitoJUnitRunner.class)
public class SignInActivityUnitTest {

    private Bundle savedInstance;

    @Test
    public void onCreateTest(){
        assertThat(SignInActivity.onCreate(savedInstance), is)
    }
}
