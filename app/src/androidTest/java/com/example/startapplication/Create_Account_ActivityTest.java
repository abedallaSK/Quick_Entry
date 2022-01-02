package com.example.startapplication;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.net.Uri;
import android.view.View;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import com.example.startapplication.classes.Account;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
@RunWith(JUnit4.class)
public class Create_Account_ActivityTest extends TestCase {

    @Rule
    public ActivityTestRule<Create_Account_Activity> mActivityTestRule = new ActivityTestRule<>(Create_Account_Activity.class);

    public Create_Account_Activity Activity;

    @Before
    public void setUp() throws Exception {
       Activity= mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        Activity.finish();
    }

    @Test
    public void userAccount() {

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                EditText edLastName = Activity.findViewById(R.id.LastName);
                EditText edId = Activity.findViewById(R.id.Id);
                EditText edEmail = Activity.findViewById(R.id.Email);
                EditText  edPassword = Activity.findViewById(R.id.Password);
                EditText  edRe_Password =Activity. findViewById(R.id.Re_Password);
                EditText edUserName = Activity.findViewById(R.id.username_normal);
                EditText    edPhone =Activity. findViewById(R.id.PhoneNumber);
                EditText edNumber=Activity.findViewById(R.id.NumberOfPeople);
                EditText  edSerialNumber=Activity.findViewById(R.id.serialNumber);

                Activity.type=1;
                Activity.profileUriData= Uri.parse("profileURI");
                Activity.imageUriData= Uri.parse("imageURI");
                Activity.test=false;
                edLastName.setText("lastName");
                edId.setText("123456789");
                edEmail.setText("email@gmail");
                edPassword.setText("password");
                edRe_Password.setText("password");
                edUserName.setText("username");
                edPhone.setText("1234567890");
                edNumber.setText("number");
                edSerialNumber.setText("102030");

                assertTrue(Activity.UserAccount(null));
                edPhone.setText("123456789");
                assertFalse(Activity.UserAccount(null));
                edPhone.setText("1234567890");
                edId.setText("12345678");
                assertFalse(Activity.UserAccount(null));
                Activity.type=2;
                edId.setText("123456789");
                assertTrue(Activity.UserAccount(null));
                edSerialNumber.setText("0");
                assertTrue(Activity.UserAccount(null));

                Activity.type=3;
                assertFalse(Activity.UserAccount(null));
                edSerialNumber.setText("102030");
                assertTrue(Activity.UserAccount(null));
                Activity.type=1;
                edRe_Password.setText("password1");
                assertFalse(Activity.UserAccount(null));
                Activity.test=true;
                assertFalse(Activity.UserAccount(null));
                Activity.test=false;
                edEmail.setText("email");
                assertFalse(Activity.UserAccount(null));

                Activity.profileUriData= null;
                assertFalse(Activity.UserAccount(null));
                Activity.profileUriData= Uri.parse("profileURI");

                edPassword.setText("pass");
                edRe_Password.setText("pass");
                assertFalse(Activity.UserAccount(null));
            }
        });
    }
}