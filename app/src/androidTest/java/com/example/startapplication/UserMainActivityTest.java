package com.example.startapplication;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.startapplication.databinding.ActivityUserMainBinding;
import com.google.android.material.navigation.NavigationView;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class UserMainActivityTest extends TestCase {


    @Rule
    public ActivityTestRule<UserMainActivity> mActivityTestRule = new ActivityTestRule<UserMainActivity>(UserMainActivity.class);

    public UserMainActivity userMainActivity;

    @Before
    public void setUp() throws Exception {
        userMainActivity = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        userMainActivity.finish();
    }


    @Test
    public void logOut() {
        AlertDialog.Builder  myAlertBuilder;
        myAlertBuilder= userMainActivity.logOut();
        Context context= myAlertBuilder.getContext();
        assertNotEquals(userMainActivity.getApplicationContext(),context);
    }



    @Test
    public void onCreate() {
        ActivityUserMainBinding binding;
        binding= userMainActivity.binding ;
        Assert.assertEquals("Android Studio",  ((TextView) binding.navViewHome.getHeaderView(0).findViewById(R.id.Name_Main)).getText().toString());
        Assert.assertEquals("android.studio@android.com",  ((TextView) binding.navViewHome.getHeaderView(0).findViewById(R.id.Email_Main)).getText().toString());
    }
}