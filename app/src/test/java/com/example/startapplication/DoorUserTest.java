package com.example.startapplication;

import com.example.startapplication.classes.DoorUser;

import org.junit.Assert;
import org.junit.Test;

public class DoorUserTest {
    DoorUser doorUser=new DoorUser("name","key","time",1);

    @Test
    public void getRank() {

        Assert.assertEquals(doorUser.getRank(),1);
        Assert.assertNotEquals(doorUser.getRank(),"1");
    }

    @Test
    public void setRank() {
        doorUser.setRank(2);
        Assert.assertEquals(doorUser.getRank(),2);
        Assert.assertNotEquals(doorUser.getRank(),"2");
    }

    @Test
    public void testGetName() {
        Assert.assertEquals(doorUser.getName(),"name");
        Assert.assertNotEquals(doorUser.getName(),"name2");
    }

    @Test
    public void testSetName() {
        Assert.assertEquals(doorUser.getName(),"name");
        Assert.assertNotEquals(doorUser.getName(),"name2");
        doorUser.setName("name3");
        Assert.assertNotEquals(doorUser.getName(),"name");
        Assert.assertEquals(doorUser.getName(),"name3");
    }

    @Test
    public void getKey() {
        Assert.assertEquals(doorUser.getKey(),"key");
        Assert.assertNotEquals(doorUser.getKey(),"name");
    }

    @Test
    public void setKey() {
        Assert.assertEquals(doorUser.getKey(),"key");
        Assert.assertNotEquals(doorUser.getKey(),"name");
        doorUser.setKey("newKey");
        Assert.assertNotEquals(doorUser.getKey(),"key");
        Assert.assertEquals(doorUser.getKey(),"newKey");
    }

    @Test
    public void getTime() {
        Assert.assertEquals(doorUser.getTime(),"time");
        Assert.assertNotEquals(doorUser.getTime(),"name");

    }

    @Test
    public void setTime() {
        Assert.assertEquals(doorUser.getTime(),"time");
        Assert.assertNotEquals(doorUser.getTime(),"name");
        doorUser.setTime("newTime");
        Assert.assertNotEquals(doorUser.getTime(),"time");
        Assert.assertEquals(doorUser.getTime(),"newTime");

    }
}