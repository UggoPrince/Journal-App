package com.example.android.journalapp;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddJournalActivityUnitTest {

    private static final String TITLE_HINT = "Title";
    private static final String BODY_HINT = "Body";
    private static final String fab_Save = "ic_menu_save";

    @Mock
    private Context mMockContext;

    @Test
    public void editTextTitleTest(){
        when(mMockContext.getString(R.string.edit_journal_title))
                .thenReturn(TITLE_HINT);

        String result = mMockContext.getString(R.string.edit_journal_title);

        assertThat(result, is(TITLE_HINT));
    }

    @Test
    public void editTextBodyTest(){
        when(mMockContext.getString(R.string.edit_journal_body))
                .thenReturn(BODY_HINT);

        String result = mMockContext.getString(R.string.edit_journal_body);

        assertThat(result, is(BODY_HINT));
    }

    @Test
    public void fabHasDrawableAttribute(){
        when(mMockContext.getString(android.R.drawable.ic_menu_save))
                .thenReturn(fab_Save);

        String result = mMockContext.getString(android.R.drawable.ic_menu_save);

        assertThat(result, is(fab_Save));
    }
}
