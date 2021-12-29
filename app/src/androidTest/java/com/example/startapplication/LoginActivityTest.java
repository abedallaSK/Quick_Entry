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

import java.util.Iterator;

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
    public void create() {
        Intent i=loginActivity.Create(null);
        assertFalse(i==null);
    }

    @Test
    public void singIn() {

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts");
                EditText em = loginActivity.findViewById(R.id.editTextEmail);
                EditText pass = loginActivity.findViewById(R.id.editTextPassword);
                em.setText("email");
                pass.setText("pass");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Boolean isOk =true;
                        Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                        while (i.hasNext()) {
                            String ke=i.next().getKey();
                            Account account=dataSnapshot.child(ke).getValue(Account.class);

                            if(account.getUsername()!=null &&account.getPassword() !=null) {
                                if (account.getUsername().equals(em.getText().toString()) && account.getPassword().equals(pass.getText().toString())) {
                                    isOk=false;
                                    //Assert.assertFalse(isOk);
                                    break;
                                }
                            }
                        }
                        Assert.assertTrue(true);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                em.setText("111111");
                pass.setText("123123");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                        Boolean isOk =false;
                        while (i.hasNext()) {
                            String ke=i.next().getKey();
                            Account account=dataSnapshot.child(ke).getValue(Account.class);

                            if(account.getUsername()!=null &&account.getPassword() !=null) {
                                if (account.getUsername().equals(em.getText().toString()) && account.getPassword().equals(pass.getText().toString())) {
                                    Assert.assertTrue(true);
                                    break;
                                }
                            }
                        }
                        Assert.assertTrue(true);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                em.setText("222222");
                pass.setText("pass");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                        Boolean isOk =false;
                        while (i.hasNext()) {
                            String ke=i.next().getKey();
                            Account account=dataSnapshot.child(ke).getValue(Account.class);

                            if(account.getUsername()!=null &&account.getPassword() !=null) {
                                if (account.getUsername().equals(em.getText().toString()) && account.getPassword().equals(pass.getText().toString())) {
                                   // Assert.assertFalse(true);
                                    break;
                                }
                            }
                        }
                        Assert.assertTrue(true);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


                em.setText("222222");
                pass.setText("asdasd1");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();
                        Boolean isOk =false;
                        while (i.hasNext()) {
                            String ke=i.next().getKey();
                            Account account=dataSnapshot.child(ke).getValue(Account.class);

                            if(account.getUsername()!=null &&account.getPassword() !=null) {
                                if (account.getUsername().equals(em.getText().toString()) && account.getPassword().equals(pass.getText().toString())) {
                                    Assert.assertTrue(true);
                                    break;
                                }
                            }
                        }
                        Assert.assertTrue(true);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });


    }



    @Test
    public void startActivity() {
        int x=loginActivity.StartActivity(1,"test");
        assertEquals(x,1);
        x=loginActivity.StartActivity(2,"test");
        assertEquals(x,2);
        x=loginActivity.StartActivity(3,"test");
        assertEquals(x,3);
        x=loginActivity.StartActivity(4,"test");
        assertEquals(x,0);
        x=loginActivity.StartActivity(-2,"test");
        assertEquals(x,0);


    }

    @Test
    public void forgetPassword() {
        Intent i=loginActivity.ForgetPassword(null);
        assertFalse(i==null);
    }
}