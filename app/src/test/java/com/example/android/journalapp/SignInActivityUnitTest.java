package com.example.android.journalapp;

import android.content.Context;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

/*
 * Unit tests for SignInActivity class
 */
@RunWith(MockitoJUnitRunner.class)
public class SignInActivityUnitTest {

    private static final String APP_NAME = "Journal App";
    private static final String EMAIL_HINT = "Email";
    private static final String PASSWORD_HINT = "Password";
    private static final String SIGN_IN_BUTTON_STRING = "SIGN IN";
    private static final String SIGN_UP_BUTTON_STRING = "SIGN UP";
    private static final String LINK_SIGN_UP = "Not Registered? Sign Up here!";
    private static final String LINK_SIGN_IN = "Have an account? Sign In here!";

    @Mock
    private Context mMockContext;

    @Test
    public void aSimpleTest(){
        assertThat(SignInActivity.LAYOUT_INFLATER_SERVICE, is("layout_inflater"));
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
    public void testSignInButtonString(){
        when(mMockContext.getString(R.string.btn_sign_in))
                .thenReturn(SIGN_IN_BUTTON_STRING);

        String result = mMockContext.getString(R.string.btn_sign_in);

        assertThat(result, is(SIGN_IN_BUTTON_STRING));
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

    @Test
    public void testSignUpLinkButton(){
        when(mMockContext.getString(R.string.btn_link_to_sign_up))
                .thenReturn(LINK_SIGN_UP);

        String result = mMockContext.getString(R.string.btn_link_to_sign_up);

        assertThat(result, is(LINK_SIGN_UP));
    }
}
