package com.example.startapplication.classes;

import static org.junit.Assert.*;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class ModelTest {

    Model model=new Model("Uri");
    Model model1=new Model();
    @Test
    public void getImageUri() {
        String x=model.getImageUri();
        Assert.assertEquals(x,"Uri");
        Assert.assertNotEquals(x,"uri");
    }

    @Test
    public void setImageUri() {
        String x=model.getImageUri();
        Assert.assertEquals(x,"Uri");
        Assert.assertNotEquals(x,"uri");
        model.setImageUri("NewUri");
        x=model.getImageUri();
        Assert.assertEquals(x,"NewUri");
        Assert.assertNotEquals(x,"Uri");

    }
}