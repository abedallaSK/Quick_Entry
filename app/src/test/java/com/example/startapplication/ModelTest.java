package com.example.startapplication;

import static org.junit.Assert.*;

import junit.framework.TestCase;

import org.junit.Test;

public class ModelTest extends TestCase {

    @Override
    public int countTestCases() {
        return super.countTestCases();
    }

    @Test
    public void getImageUri() {
        Model model=new Model("test");
        assertEquals(model.getImageUri(),"test");

    }

    @Test
    public void setImageUri() {
        Model model=new Model("test");

        model.setImageUri("new");
        assertEquals(model.getImageUri(),"new");
    }
}