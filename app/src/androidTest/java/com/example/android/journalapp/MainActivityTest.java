package com.example.android.journalapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void fabClick_Does_Not_Fail(){
        onView(withId(R.id.fab_add_journal))
                .perform(click());
    }

    @Test
    public void menuClick_Exist(){
        onView(withId(R.menu.journal_menu));
    }

    @Test
    public void menuItems_Exists(){
        onView(withId(R.string.action_profile));
        onView(withId(R.string.action_delete_all));
        onView(withId(R.string.action_sign_out));
    }
}
