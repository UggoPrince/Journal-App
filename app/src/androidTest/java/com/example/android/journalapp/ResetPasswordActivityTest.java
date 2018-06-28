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
public class ResetPasswordActivityTest {

    // test email string;
    private String testEmail;

    @Rule
    public ActivityTestRule<ResetPasswordActivity> mActivityRule =
            new ActivityTestRule<>(ResetPasswordActivity.class);

    @Before
    // initialize test email
    public void initStrings(){
        testEmail = "name@email.com";
    }

    @Test
    public void scrollViewTest(){
        onView(withId(R.id.btn_back)).
                perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void textViewsTest(){
        onView(withId(R.id.reset_pw_tv1));
        onView(withId(R.id.reset_pw_tv2));
    }

    @Test
    public void editTextViewTest(){
        onView(withId(R.id.reset_pw_email))
                .perform(typeText(testEmail));
    }

    @Test
    public void resetPasswordButtonTest(){
        onView(withId(R.id.btn_reset_password))
                .perform(click());
    }

    @Test
    public void backButtonTest(){
        onView(withId(R.id.btn_back))
                .perform(click());
    }
}
