package com.example.startapplication;

import android.view.Menu;

import com.example.startapplication.databinding.ActivityUserMainBinding;

import junit.framework.TestCase;


public class UserMainActivityTest extends TestCase {
    UserMainActivity userMainActivity;

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {

    }


    public void testOnCreateOptionsMenu() {
        Menu menu= null;
        assertEquals(userMainActivity.onCreateOptionsMenu(menu),true);
    }

    public void testOnSupportNavigateUp() {
        assertEquals(userMainActivity.onSupportNavigateUp(),false);
    }

    public void testLogOut() {
        assertEquals(userMainActivity.logOut(null),false);
    }
}