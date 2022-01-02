package com.example.startapplication;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;


import android.content.Intent;

import com.google.firebase.FirebaseApp;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest extends TestCase {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void create() {

       /* LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        FirebaseApp.initializeApp(activity);
        activity.findViewById(R.id.btCreate).performClick();

        Intent expectedIntent = new Intent(activity, Create_Account_Activity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());*/
    }

    @Test
    public void singIn() {

    }

    @Test
    public void startActivity() {
    }

    @Test
    public void forgetPassword() {
    }
}