package com.example.startapplication;


import junit.framework.TestCase;

import org.junit.Assert;


public class StartActivityTest extends TestCase {

    LoginActivity loginActivity;

    public void testStartActivity() throws InstantiationException, IllegalAccessException {
        Assert.assertEquals(loginActivity.getClass().newInstance().StartActivity(1, "test"), "OK");

    }
}
