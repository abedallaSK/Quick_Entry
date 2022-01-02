package com.example.startapplication;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.example.startapplication.databinding.ActivityUserMainBinding;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)

public class BusinessMainActivityTest extends TestCase {

    @Rule
    public ActivityTestRule<BusinessMainActivity> mActivityTestRule = new ActivityTestRule<>(BusinessMainActivity.class);

    public BusinessMainActivity Activity;
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
    public void goLogin() {
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

                Activity.goLogin();


                assertEquals(mSettings.getString(DATA_TAG, ""),"");
                assertEquals(mSettings.getInt("Type", -1),-1);
                assertNotEquals(mSettings.getString(DATA_TAG, ""),"mykey");
                assertNotEquals(mSettings.getInt("Type", -1),1);


                editor.putString(DATA_TAG, "test2");
                editor.putInt("Type",4);
                editor.commit();
                Activity.goLogin();

                assertEquals(mSettings.getString(DATA_TAG, ""),"");
                assertEquals(mSettings.getInt("Type", -1),-1);
                assertNotEquals(mSettings.getString(DATA_TAG, ""),"test2");
                assertNotEquals(mSettings.getInt("Type", -1),4);

            }


    });
    }


}