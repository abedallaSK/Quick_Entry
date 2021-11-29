package com.example.startapplication;

import static org.junit.Assert.*;

import android.view.View;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class LoginActivityTest {

    LoginActivity loginActivity;

    @Test
    public void create() throws InstantiationException, IllegalAccessException {
        Assert.assertEquals(loginActivity.getClass().newInstance().Create(null), "OK");
    }

    @Test
    public void singIn() throws InstantiationException, IllegalAccessException {
        assertNotEquals(loginActivity.getClass().newInstance().SingIn(null),null);
    }

    @Test
    public void startActivity() throws InstantiationException, IllegalAccessException {

        Assert.assertEquals(loginActivity.getClass().newInstance().StartActivity(1, "test"), "OK");
    }

}