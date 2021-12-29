package com.example.startapplication;

import static org.junit.Assert.*;

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

import java.util.Iterator;

@RunWith(JUnit4.class)
public class UserActivityTest extends TestCase {


    @Rule
    public ActivityTestRule<UserActivity> mActivityTestRule = new ActivityTestRule<UserActivity>(UserActivity.class);

    public UserActivity UserActivity;

    @Before
    public void setUp() throws Exception {
        UserActivity = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        UserActivity.finish();
    }



    @Test
    public void delete() {
        UserActivity.key="ssf";
        UserActivity.delete(null);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();

                    Account account=dataSnapshot.child( UserActivity.key).getValue(Account.class);
               if(account!=null) Assert.assertTrue(true);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Test
    public void save() {
    }

    @Test
    public void photoShow() {
    }

    @Test
    public void send() {
    }
}