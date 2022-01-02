package com.example.startapplication;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

public class ForgetPasswordActivityTest extends TestCase {


    @Rule
    public ActivityTestRule<ForgetPasswordActivity> mActivityTestRule = new ActivityTestRule<>(ForgetPasswordActivity.class);

    public ForgetPasswordActivity Activity;

    @Before
    public void setUp() throws Exception {
        Activity = mActivityTestRule.getActivity();

    }

    @After
    public void tearDown() throws Exception {
        Activity.finish();
    }


    @Test
    public void check() throws Exception{

        List<Account> accounts=new ArrayList<>();
        accounts.add(new Account("111111","name","familyName","123123","abadalla1999@gmail.com","phone","id",1,"photo","photo","code1"));
        accounts.add(new Account("222222","name","familyName","asdasd1","dev@gmail.com","phone","id",1,"photo","photo","code1"));
        accounts.add(new Account("333333","name","familyName","333123","email","phone","id",1,"photo","photo","code1"));

        Activity.accounts=accounts;
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                EditText editText_Email = Activity. findViewById(R.id.editTextTextPersonName3);
                EditText  editText_Username = Activity. findViewById(R.id.editTextTextPersonName2);
                EditText edPasswordShow= Activity.findViewById(R.id.passwordShow);
                Spinner spinner = Activity.findViewById(R.id.planets_spinner);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Activity,
                        R.array.user_list, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                edPasswordShow.setText("");
                editText_Email.setText("abadalla1999@gmail.com");
                editText_Username.setText("111111");
                Activity.Check(null);
                assertEquals(edPasswordShow.getText().toString(),"123123");

                edPasswordShow.setText("");
                editText_Email.setText("abadalla1999@gmail.com");
                editText_Username.setText("111111");
                Activity.Check(null);
                assertNotEquals(edPasswordShow.getText().toString(),"123456");

                edPasswordShow.setText("");
                editText_Email.setText("abadalla1999");
                editText_Username.setText("111111");
                Activity.Check(null);
                assertNotEquals(edPasswordShow.getText().toString(),"123123");

                edPasswordShow.setText("");
                editText_Email.setText("abadalla1999@gmail.com");
                editText_Username.setText("222222");
                spinner.setSelection(1);
                Activity.Check(null);
                assertNotEquals(edPasswordShow.getText().toString(),"123123");

                edPasswordShow.setText("");
                editText_Email.setText("abadalla1999@gmail.com");
                editText_Username.setText("222222");
                spinner.setSelection(2);
                Activity.Check(null);
                assertNotEquals(edPasswordShow.getText().toString(),"123123");

                edPasswordShow.setText("");
                editText_Email.setText("dev@gmail.com");
                editText_Username.setText("222222");
                spinner.setSelection(1);
                Activity.Check(null);
                assertNotEquals(edPasswordShow.getText().toString(),"asdasd1");
            }
        });
    }
}