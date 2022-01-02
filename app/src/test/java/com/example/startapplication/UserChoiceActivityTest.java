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
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class UserChoiceActivityTest extends TestCase {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void normalAccount() {
     /*  UserMainActivity activity = Robolectric.setupActivity(UserMainActivity.class);
        FirebaseApp.initializeApp(activity);
        activity.findViewById(R.id.button2).performClick();

        Intent expectedIntent = new Intent(activity, Create_Account_Activity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());*/
    }

    @Test
    public void businessAcount() {
        /*UserMainActivity activity = Robolectric.setupActivity(UserMainActivity.class);
        FirebaseApp.initializeApp(activity);
        activity.findViewById(R.id.button7).performClick();

        Intent expectedIntent = new Intent(activity, Create_Account_Activity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());*/
    }

    @Test
    public void foremanAccount() {
       /* UserMainActivity activity = Robolectric.setupActivity(UserMainActivity.class);
        FirebaseApp.initializeApp(activity);
        activity.findViewById(R.id.button8).performClick();

        Intent expectedIntent = new Intent(activity, Create_Account_Activity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());*/
    }

    @Test
    public void help() {
      /*  UserMainActivity activity = Robolectric.setupActivity(UserMainActivity.class);
        FirebaseApp.initializeApp(activity);
        activity.findViewById(R.id.button13).performClick();

        Intent expectedIntent = new Intent(activity, HelpActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());*/
    }
}