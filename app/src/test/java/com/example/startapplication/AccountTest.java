package com.example.startapplication;

import static org.junit.Assert.*;

import com.example.startapplication.classes.Account;

import org.junit.Test;

public class AccountTest {
        Account account=new Account("username","name","familyName","password","email","phoneNumber","id",1,"profileUri","greenUri");
    @Test
    public void getId() {
        assertEquals(account.getId(),"id");
    }

    @Test
    public void setId() {
        account.setId("newId");
        assertEquals(account.getId(),"newId");
    }

    @Test
    public void getProfileUri() {
        assertEquals(account.getProfileUri(),"profileUri");
    }

    @Test
    public void setProfileUri() {
        account.setProfileUri("NewProfileUri");
        assertEquals(account.getProfileUri(),"NewProfileUri");
    }


    @Test
    public void testGetName() {
        assertEquals(account.getName(),"name");
    }

    @Test
    public void testSetName() {
        account.setName("newName");
        assertEquals(account.getName(),"newName");
    }

    @Test
    public void getFamilyName() {
        assertEquals(account.getFamilyName(),"familyName");
    }

    @Test
    public void setFamilyName() {
        account.setFamilyName("newFamilyName");
        assertEquals(account.getFamilyName(),"newFamilyName");
    }

    @Test
    public void getUsername() {
        assertEquals(account.getUsername(),"username");
    }

    @Test
    public void setUsername() {
        account.setUsername("NewUserName");
        assertEquals(account.getUsername(),"NewUserName");
    }

    @Test
    public void getPassword() {
        assertEquals(account.getPassword(),"password");
    }

    @Test
    public void setPassword() {
        account.setPassword("NewPassword");
        assertEquals(account.getPassword(),"NewPassword");
    }

    @Test
    public void getEmail() {
        assertEquals(account.getEmail(),"email");
    }

    @Test
    public void setEmail() {
        account.setEmail("NewEmail");
        assertEquals(account.getEmail(),"NewEmail");
    }

    @Test
    public void getPhoneNumber() {
        assertEquals(account.getPhoneNumber(),"phoneNumber");
    }

    @Test
    public void setPhoneNumber() {
        account.setPhoneNumber("NewPhoneNumber");
        assertEquals(account.getPhoneNumber(),"NewPhoneNumber");
    }

    @Test
    public void getGreenUri() {
        assertEquals(account.getGreenUri(),"greenUri");
    }

    @Test
    public void setGreenUri() {
        account.setGreenUri("NewGreenUri");
        assertEquals(account.getGreenUri(),"NewGreenUri");
    }

    @Test
    public void getType() {
       assertEquals(account.getType(),1);
    }

    @Test
    public void setType() {
        account.setType(2);
        assertEquals(account.getType(),2);
    }
}