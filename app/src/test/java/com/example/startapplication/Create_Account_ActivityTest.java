package com.example.startapplication;

import static org.junit.Assert.*;

import android.widget.EditText;

import org.junit.Test;

public class Create_Account_ActivityTest {


    private EditText edName;
    private EditText edLastName;
    private EditText edId;
    private EditText edPhone;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edRe_Password;
Create_Account_Activity create_account_activity;
    @Test
    public void userAccount() {
         edName.setText("Name");
        edLastName.setText("LastName");
        edId.setText("Id");
        edPhone.setText("phone");
        assertEquals(create_account_activity.UserAccount(null),false);

    }


    @Test
    public void storage() {
        assertEquals(create_account_activity.Storage(null),true);
    }

    @Test
    public void cheekUseName() {
        assertEquals(create_account_activity.CheekUseName(null),true);
    }
}