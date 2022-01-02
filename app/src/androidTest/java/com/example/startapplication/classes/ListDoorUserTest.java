package com.example.startapplication.classes;

import static org.junit.Assert.*;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

public class ListDoorUserTest {


    private ListDoorUser listDoorUser =new ListDoorUser();

    @Test
    public void addUser() {

        DoorUser doorUser=new DoorUser("name1","key","time",1);
        listDoorUser.addUser(doorUser);
       String name= listDoorUser.getDoorUsers().get(0).getName();
       assertEquals(name,"name1");

        DoorUser doorUser2=new DoorUser("name5","key","time",5);
        listDoorUser.addUser(doorUser2);
        String name5= listDoorUser.getDoorUsers().get(0).getName();
        assertEquals(name5,"name5");

        DoorUser doorUser3=new DoorUser("name2","key","time",2);
        listDoorUser.addUser(doorUser3);
        String name2= listDoorUser.getDoorUsers().get(0).getName();
        assertEquals(name2,"name2");
        name5= listDoorUser.getDoorUsers().get(1).getName();
        assertEquals(name5,"name5");
    }

    @Test
    public void removeUser() {
        DoorUser doorUser=new DoorUser("name1","key","time",1);
        listDoorUser.addUser(doorUser);
        String name= listDoorUser.getDoorUsers().get(0).getName();
        assertEquals(name,"name1");

        DoorUser doorUser2=new DoorUser("name5","key","time",5);
        listDoorUser.addUser(doorUser2);
        String name5= listDoorUser.getDoorUsers().get(0).getName();
        assertEquals(name5,"name5");

        DoorUser doorUser3=new DoorUser("name2","key","time",2);
        listDoorUser.addUser(doorUser3);
        String name2= listDoorUser.getDoorUsers().get(0).getName();
        assertEquals(name2,"name2");
        name5= listDoorUser.getDoorUsers().get(1).getName();
        assertEquals(name5,"name5");

        listDoorUser.removeUser(1);
        assertNotEquals(listDoorUser.getDoorUsers().get(0).getName(),"name5");
        assertEquals(listDoorUser.getDoorUsers().get(0).getName(),"name2");
        listDoorUser.removeUser(0);
        assertNotEquals(listDoorUser.getDoorUsers().get(0).getName(),"name2");
        assertEquals(listDoorUser.getDoorUsers().get(0).getName(),"name1");

    }

    @Test
    public void getDoorUsers() {
        DoorUser doorUser=new DoorUser("name1","key","time",1);
        listDoorUser.addUser(doorUser);
        String name= listDoorUser.getDoorUsers().get(0).getName();
        assertEquals(name,"name1");

        DoorUser doorUser2=new DoorUser("name5","key","time",5);
        listDoorUser.addUser(doorUser2);
        String name5= listDoorUser.getDoorUsers().get(0).getName();
        assertEquals(name5,"name5");

        DoorUser doorUser3=new DoorUser("name2","key","time",2);
        listDoorUser.addUser(doorUser3);
        String name2= listDoorUser.getDoorUsers().get(0).getName();
        assertEquals(name2,"name2");
        name5= listDoorUser.getDoorUsers().get(1).getName();
        assertEquals(name5,"name5");

        ArrayList<DoorUser> newDoorUser=listDoorUser.getDoorUsers();
        assertEquals(newDoorUser.get(0),doorUser3);
        assertNotEquals(newDoorUser.get(0),doorUser2);
        assertEquals(newDoorUser.get(1),doorUser2);
        assertEquals(newDoorUser.get(2),doorUser);
        assertNotEquals(newDoorUser.get(0),doorUser2);

    }

    @Test
    public void setDoorUsers() {
        DoorUser doorUser=new DoorUser("name1","key","time",1);
        DoorUser doorUser2=new DoorUser("name5","key","time",5);
        DoorUser doorUser3=new DoorUser("name2","key","time",2);

        ArrayList<DoorUser> doorUserArrayList=new ArrayList<>();
        doorUserArrayList.add(doorUser);
        doorUserArrayList.add(doorUser2);
        doorUserArrayList.add(doorUser3);
        listDoorUser.setDoorUsers(doorUserArrayList);

        ArrayList<DoorUser> newDoorUser=listDoorUser.getDoorUsers();
        assertEquals(newDoorUser.get(0),doorUser);
        assertNotEquals(newDoorUser.get(0),doorUser2);
        assertEquals(newDoorUser.get(1),doorUser2);
        assertEquals(newDoorUser.get(2),doorUser3);
        assertNotEquals(newDoorUser.get(0),doorUser2);

    }
}