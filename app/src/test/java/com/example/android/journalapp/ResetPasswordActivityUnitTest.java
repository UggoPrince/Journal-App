package com.example.android.journalapp;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/*
 * Unit tests for ResetPasswordActivity class
 */
@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordActivityUnitTest {

    private static final String TEXT_VIEW_1 = "Forgot Password?";
    private static final String TEXT_VIEW_2 = "Type in your email to get password reset instructions.";
    private static final String EMAIL_HINT = "Email";
    private static final String BUTTON_RESET_PASSWORD = "RESET PASSWORD";
    private static final String BUTTON_BACK = "<< BACK";

    @Mock
    private Context mMockContext;

    @Test
    public void textView_1_Test(){
        when(mMockContext.getString(R.string.reset_pw_first_tv))
                .thenReturn(TEXT_VIEW_1);

        String result = mMockContext.getString(R.string.reset_pw_first_tv);

        assertThat(result, is(TEXT_VIEW_1));
    }

    @Test
    public void textView_2_Test(){
        when(mMockContext.getString(R.string.reset_pw_second_tv))
                .thenReturn(TEXT_VIEW_2);

        String result = mMockContext.getString(R.string.reset_pw_second_tv);

        assertThat(result, is(TEXT_VIEW_2));
    }

    @Test
    public void emailHintTest(){
        when(mMockContext.getString(R.string.hint_email))
                .thenReturn(EMAIL_HINT);

        String result = mMockContext.getString(R.string.hint_email);

        assertThat(result, is(EMAIL_HINT));
    }

    @Test
    public void buttonResetPasswordTest(){
        when(mMockContext.getString(R.string.btn_reset_password))
                .thenReturn(BUTTON_RESET_PASSWORD);

        String result = mMockContext.getString(R.string.btn_reset_password);

        assertThat(result, is(BUTTON_RESET_PASSWORD));
    }

    @Test
    public void buttonBackTest(){
        when(mMockContext.getString(R.string.btn_back_leave_reset_pw))
                .thenReturn(BUTTON_BACK);

        String result = mMockContext.getString(R.string.btn_back_leave_reset_pw);

        assertThat(result, is(BUTTON_BACK));
    }
}
