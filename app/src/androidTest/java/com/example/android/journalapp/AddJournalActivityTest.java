package com.example.android.journalapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AddJournalActivityTest {

    // declare test Title and Body of Journal;
    private String testTitle;
    private String testBody;

    @Rule
    public ActivityTestRule<AddJournalActivity> mActivityRule =
            new ActivityTestRule<>(AddJournalActivity.class);

    @Before
    // initialize test title and body
    public void initStrings(){
        testTitle = "name@email.com";
        testBody = "klju45ik";
    }

    @Test
    public void editTextTitle_Works_As_Expected(){
        // its exits in the layout
        onView(withId(R.id.editTextJournalTitle));
        onView(withId(R.id.editTextJournalBody));

        // can type text
        onView(withId(R.id.editTextJournalTitle))
                .perform(typeText(testTitle));
        onView(withId(R.id.editTextJournalBody))
                .perform(typeText(testBody));
    }

    @Test
    public void fabClick_To_Save_Journal_Is_Clickable(){
        onView(withId(R.id.fab_save))
                .perform(click());
    }
}
