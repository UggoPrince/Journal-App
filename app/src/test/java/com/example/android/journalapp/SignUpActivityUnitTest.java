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
public class SignUpActivityUnitTest {
    private static final String APP_NAME = "Journal App";
    private static final String EMAIL_HINT = "Email";
    private static final String PASSWORD_HINT = "Password";
    private static final String SIGN_UP_BUTTON_STRING = "SIGN UP";
    private static final String LINK_SIGN_IN = "Have an account? Sign In here!";

    @Mock
    private Context mMockContext;

    @Test
    public void aSimpleTest(){
        assertThat(SignUpActivity.LAYOUT_INFLATER_SERVICE, is("layout_inflater"));
    }

    @Test
    public void testAppName(){
        when(mMockContext.getString(R.string.app_name))
                .thenReturn(APP_NAME);

        String result = mMockContext.getString(R.string.app_name);

        assertThat(result, is(APP_NAME));
    }

    @Test
    public void testEmailHint(){
        when(mMockContext.getString(R.string.hint_email))
                .thenReturn(EMAIL_HINT);

        String result = mMockContext.getString(R.string.hint_email);

        assertThat(result, is(EMAIL_HINT));
    }

    @Test
    public void testPasswordHint(){
        when(mMockContext.getString(R.string.hint_password))
                .thenReturn(PASSWORD_HINT);

        String result = mMockContext.getString(R.string.hint_password);

        assertThat(result, is(PASSWORD_HINT));
    }

    @Test
    public void testSignUpButtonString(){
        when(mMockContext.getString(R.string.btn_sign_up))
                .thenReturn(SIGN_UP_BUTTON_STRING);

        String result = mMockContext.getString(R.string.btn_sign_up);

        assertThat(result, is(SIGN_UP_BUTTON_STRING));
    }

    @Test
    public void testSignInLinkButton(){
        when(mMockContext.getString(R.string.btn_link_to_sign_in))
                .thenReturn(LINK_SIGN_IN);

        String result = mMockContext.getString(R.string.btn_link_to_sign_in);

        assertThat(result, is(LINK_SIGN_IN));
    }
}
