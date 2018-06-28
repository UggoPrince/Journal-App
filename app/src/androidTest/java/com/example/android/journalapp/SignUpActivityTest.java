package com.example.android.journalapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    // declare test email and password variables;
    private String testEmail;
    private String testPassword;

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityRule =
            new ActivityTestRule<>(SignUpActivity.class);

    @Before
    // initialize test email and password variables
    public void initStrings(){
        testEmail = "name@email.com";
        testPassword = "klju45ik";
    }

    @Test
    public void scrollViewTest(){
        onView(withId(R.id.btn_link_for_sign_in)).
                perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void editTextViewsTest(){
        //check if email and password EditText Views exits
        onView(withId(R.id.sign_up_email));
        onView(withId(R.id.sign_up_password));

        // test if text could be typed in both EditText Views
        onView(withId(R.id.sign_up_email)).
                perform(typeText(testEmail));

        onView(withId(R.id.sign_up_password)).
                perform(typeText(testPassword));
    }

    @Test
    public void signInButtonTest(){
        onView(withId(R.id.btn_sign_up)).perform(click());
    }

    @Test
    public void signInLinkButtonTest(){
        onView(withId(R.id.btn_link_for_sign_in));
        onView(withId(R.id.btn_link_for_sign_in)).perform(click());
    }
}
