package com.example.startapplication;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.test.rule.ActivityTestRule;

import com.example.startapplication.classes.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(JUnit4.class)

public class LoginActivityTest extends TestCase {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    public LoginActivity loginActivity;

    @Before
    public void setUp() throws Exception {
        loginActivity = mActivityTestRule.getActivity();

    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }


    @Test
    public void isCorrectUser()throws Exception {
        List<Account> accounts=new ArrayList<>();
        accounts.add(new Account("111111","name","familyName","123123","email","phone","id",1,"photo","photo","code1"));
        accounts.add(new Account("222222","name","familyName","asdasd1","email","phone","id",1,"photo","photo","code1"));
        accounts.add(new Account("333333","name","familyName","333123","email","phone","id",1,"photo","photo","code1"));

        loginActivity.accounts=accounts;

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                EditText em = loginActivity.findViewById(R.id.editTextEmail);
                EditText pass = loginActivity.findViewById(R.id.editTextPassword);

                em.setText("email");
                pass.setText("pass");
                assertEquals(loginActivity.IsCorrectUser(em.getText().toString(), pass.getText().toString()), -1);

                em.setText("111111");
                pass.setText("pass");
                assertEquals(loginActivity.IsCorrectUser(em.getText().toString(), pass.getText().toString()), -1);

                em.setText("111111");
                pass.setText("123123");
                assertEquals(loginActivity.IsCorrectUser(em.getText().toString(), pass.getText().toString()), 0);

                em.setText("222222");
                pass.setText("asdasd1");
                assertEquals(loginActivity.IsCorrectUser(em.getText().toString(), pass.getText().toString()), 1);
            }
        });
    }

}