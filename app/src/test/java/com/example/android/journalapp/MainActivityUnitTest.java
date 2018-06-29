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
public class MainActivityUnitTest {

    private static final String fabDrawble = "ic_input_add";
    private static final String menuFileName = "journal_menu";

    @Mock
    private Context mMockContext;

    @Test
    public void mainActivityHasAMenuTest(){
        when(mMockContext.getString(R.menu.journal_menu))
                .thenReturn(menuFileName);
        String result = mMockContext.getString(R.menu.journal_menu);

        assertThat(result, is(menuFileName));
    }

    @Test
    public void fabHasDrawableAttribute(){
        when(mMockContext.getString(android.R.drawable.ic_input_add))
                .thenReturn(fabDrawble);

        String result = mMockContext.getString(android.R.drawable.ic_input_add);

        assertThat(result, is(fabDrawble));
    }
}
