package com.example.startapplication;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.content.SharedPreferences;

import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class BusinessDoorActivityTest extends TestCase {
    @Rule
    public ActivityTestRule<BusinessDoorActivity> mActivityTestRule = new ActivityTestRule<>(BusinessDoorActivity.class);

    public BusinessDoorActivity Activity;
    private static final String PREFS_NAME = "LOGIN";
    private static final String DATA_TAG = "KEY";

    @Before
    public void setUp() throws Exception {
        Activity = mActivityTestRule.getActivity();

    }

    @After
    public void tearDown() throws Exception {
        Activity.finish();
    }


    @Test
    public void logOut() {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                SharedPreferences mSettings = Activity.getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(DATA_TAG, "mykey");
                editor.putInt("Type",1);
                editor.commit();

                assertEquals(mSettings.getString(DATA_TAG, ""),"mykey");
                assertEquals(mSettings.getInt("Type", -1),1);
                assertNotEquals(mSettings.getString(DATA_TAG, ""),"");
                assertNotEquals(mSettings.getInt("Type", -1),-1);

                Activity.logOut_remove();


                assertEquals(mSettings.getString(DATA_TAG, ""),"");
                assertEquals(mSettings.getInt("Type", -1),-1);
                assertNotEquals(mSettings.getString(DATA_TAG, ""),"mykey");
                assertNotEquals(mSettings.getInt("Type", -1),1);


                editor.putString(DATA_TAG, "test2");
                editor.putInt("Type",4);
                editor.commit();
                Activity.logOut_remove();

                assertEquals(mSettings.getString(DATA_TAG, ""),"");
                assertEquals(mSettings.getInt("Type", -1),-1);
                assertNotEquals(mSettings.getString(DATA_TAG, ""),"test2");
                assertNotEquals(mSettings.getInt("Type", -1),4);
            }
        });
    }
}